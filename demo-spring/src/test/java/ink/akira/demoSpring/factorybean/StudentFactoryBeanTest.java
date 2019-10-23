package ink.akira.demoSpring.factorybean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
}
