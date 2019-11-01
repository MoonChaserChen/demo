package ink.akira.demoSpring.factorybean;

import ink.akira.demoSpring.pojo.Student;
import org.junit.Test;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Date;

public class StudentFactoryBeanTest {
    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        Object bean = context.getBean("studentFactoryBean");
        System.out.println(bean.getClass().getName());
        System.out.println(bean);
        Object bean1 = context.getBean("&studentFactoryBean");
        System.out.println(bean1.getClass().getName());
    }

    @Test
    public void testSingleton(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        AutowireCapableBeanFactory autowireCapableBeanFactory = context.getAutowireCapableBeanFactory();
        DefaultListableBeanFactory de = (DefaultListableBeanFactory) autowireCapableBeanFactory;
        de.registerSingleton("abc", new Student(1002, "Allen"));
        Object bean = context.getBean("studentFactoryBean");
        Object bean1 = context.getBean("studentFactoryBean");
        System.out.println(de.getSingletonCount());
        Arrays.stream(de.getSingletonNames()).forEach(System.out::println);
        System.out.println(bean == bean1);
        context.close();
    }

    @Test
    public void test0(){
        System.out.println(new Date(1572335636565L));
        Date date = new Date(119, 9, 29, 16, 0, 0);
        System.out.println(date.getTime());
    }
}
