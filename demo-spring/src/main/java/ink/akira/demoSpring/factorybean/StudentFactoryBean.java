package ink.akira.demoSpring.factorybean;

import ink.akira.demoSpring.pojo.Student;
import org.springframework.beans.factory.SmartFactoryBean;

public class StudentFactoryBean implements SmartFactoryBean<Student> {
    @Override
    public Student getObject() throws Exception {
        return new Student(1001, "Akira");
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }

    @Override
    public boolean isPrototype() {
        return false;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
