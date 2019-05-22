## WEB项目防重设计与分布式锁

### 问题引入

考虑这样一个场景：一个提供免费书籍的网站，用户通过某种操作（比如点击页面上“免费获取”的按钮）以获取某本书籍，如何避免用户连续点击两次等情况（两次同样的请求）导致用户获取了两本同样的书籍（这样用户在“我的书单”中就能看到两本同样的书籍）。

### 数据库表说明：

```
create table user_book(
    id bigint unsigned auto_increment comment '主键id',
    user_id bigint unsigned default 0 comment '用户id',
    book_id bigint unsigned default 0 comment '书籍id',
    primary key (id),
    key idx_user_id (user_id),
    key idx_book_id (book_id)
);
```

### 最简易的处理：

最简易的处理是：在接收到用户请求后，先判断用户是否已经拥有此书籍，若已拥有，则页面上给予提示“已拥有此书籍”，大致代码如下：
```java
@RequestMapping(value="/getBook")
public JsonResult getBook(String userId, String bookId) {
    logger.info("===== userId: {}, bookId: {}",userId, bookId);
    List<UserBook> userBook = akiraDAO.getUserBook(userId, bookId);
    if (userBook.isEmpty()) {
        akiraDAO.insertBook(userId, bookId);
        return new JsonResult(0, "OK", null);
    } else {
        return new JsonResult(-1, "Already have this book", null);
    }
}
```

假设userId:0获取bookId:4的书籍，第一次访问：

![simple_handle_visit](http://image.akira.ink/demo-duplicate-request/simple_handle_visit.png)

在第二次访问时：

![simple_handle_visit1](http://image.akira.ink/demo-duplicate-request/simple_handle_visit1.png)

### 高并发下的情况：

但是上面的简易处理是有问题的，在高并发下会将此问题暴露出来，下面我模拟一个“高并发”访问，大致代码如下：

首先定义一个线程类用于访问上面的接口：

```java
class Visitor implements Runnable{
    private String targetUrl;

    public Visitor() {
    }

    public Visitor(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public void visit(String targetURL) {
        try {
            URL url = new URL(targetURL);
            url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        visit(this.targetUrl);
    }
}

```

然后启动线程池来模拟一系列的接口访问：
```java
 public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    long userId = 0L;
    // 模拟当前用户获取十本书
    for (int i = 0; i < 10; i++) {
        String param = "?userId=" + userId + "&bookId=" + i;
        String url = "http://localhost:8080/getBook" + param;
        // 每本书的获取重复一次
        for (int i1 = 0; i1 < 2; i1++) {
            executorService.submit(new Visitor(url));
        }
    }
    executorService.shutdown();
}
```
> 这里用userId为0的用户模拟获取bookId分别为0到9的十本书，且每本书均重复获取一次。

* 访问记录：

![high_concurrency_visit_record](http://image.akira.ink/demo-duplicate-request/high_concurrency_visit_record.png)

> 可以看到，同一个人在较短时间内获取了同一本书两次，且由不同的线程去执行。

* 查看数据库结果： select * from user_book order by book_id;

![high_concurrency_result](http://image.akira.ink/demo-duplicate-request/high_concurrency_result.png)

* 产生原因：

    判断是否拥有书籍与获取书籍不是原子性的，在不同的线程执行的情况下，可能均同时通过了第一步的验证（此时判断用户未拥有 书籍），然后在后面的获取书籍时均顺利地执行下去。

### 锁的引入：

对上面的情况解决办法是引入锁保证某个线程在执行此接口的代码时不被其它线程中断。
```java
@RequestMapping(value="/getBook")
public JsonResult getBook(String userId, String bookId) {
    logger.info("===== userId: {}, bookId: {}",userId, bookId);
    synchronized (this) {
        List<UserBook> userBook = akiraDAO.getUserBook(userId, bookId);
        if (userBook.isEmpty()) {
            akiraDAO.insertBook(userId, bookId);
            return new JsonResult(0, "OK", null);
        } else {
            return new JsonResult(-1, "Already have this book", null);
        }
    }
}
```
> 注意这里的this指的是当前Controller的实例，由于在Spring中Controller默认是单例的，因此所有的this均是同一个对象。不过这样势必会降低此接口的并发量。

### 分布式锁：
若是这个服务部署了多个点，并通过nginx进行负载均衡。那么由于两次访问被反向代理到不同的部署点，不在同一个JVM进程里，因此这里的锁则不能是synchronized来形成了，这里的锁则应该是分布式的，分布式锁的形成方式有很多，这里介绍用Redis形成的分布式锁来处理重复请求，代码如下：
```java
@RequestMapping(value="/getBook")
public JsonResult getBook(String userId, String bookId) {
    logger.info("===== userId: {}, bookId: {}",userId, bookId);
    String lockKey = "lck:" + userId + ":" + bookId;
    try {
        if (jedisDAO.getLock(lockKey, String.valueOf(new Date()))) {
            List<UserBook> userBook = akiraDAO.getUserBook(userId, bookId);
            if (userBook.isEmpty()) {
                akiraDAO.insertBook(userId, bookId);
                return new JsonResult(0, "OK", null);
            } else {
                return new JsonResult(-1, "Already have this book", null);
            }
        } else {
            return new JsonResult(-1, "Getting this book currently", null);
        }
    } finally {
        jedisDAO.delete(lockKey);
    }
}
```

getLock(..)与delete(..)如下：

```java
public boolean getLock(String lockKey, String identifier) {
    try (Jedis jedis = jedisPool.getResource()) {
        return jedis.setnx(lockKey, identifier) == 1L;
    }
}

public void delete(String key) {
    try (Jedis jedis = jedisPool.getResource()) {
        jedis.del(key);
    }
}
```

* 原理：

针对userId与bookId形成一个key，当用户userId尝试获取一本书籍bookId时，则组成的key存放到redis中（这时表示用户正在获取此书籍），尝试获取完成后（不管获取成功与否）均删除之前的key。由于jedis.setnx是指当key不存在时，才设置成功，且这个操作具有原子性，因此保证了只有一个请求能进入后面的代码段执行，实现了锁的功效，即这个请求能获取到“锁”，其它请求获取不到，这里直接返回失败，不再继续重试。

### 总结：
对于这种  “验证” ---> “操作”模式，如果后面的操作对前面验证存在着影响，则应该保证这两步操作的原子性。这里的操作包括但不限于数据库操作。

### PS:
1. 这里的数据库其实设计得不合理，对于此种情况userId与bookId应设置一唯一性约束，保证数据的正确性
2. 对于此例中的按钮触发“获取书籍”，应在前端也加以限制，不得连续点击两次
3. redis可形成其它锁，比如LockWithTimeout（锁超时自动释放）、比如TryLock（获取锁失败后继续尝试）、比如TryLockWithTime（获取锁失败后继续尝试，但超过一定时间后放弃）
