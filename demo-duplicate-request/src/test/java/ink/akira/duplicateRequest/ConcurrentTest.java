package ink.akira.duplicateRequest;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by akira on 2019/2/21.
 */
public class ConcurrentTest {
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
}

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
