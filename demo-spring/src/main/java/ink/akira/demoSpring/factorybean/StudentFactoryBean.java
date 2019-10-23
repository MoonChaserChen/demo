package ink.akira.demoSpring.factorybean;

import ink.akira.demoSpring.pojo.Student;
import org.springframework.beans.factory.FactoryBean;

public class StudentFactoryBean implements FactoryBean<Student> {
    @Override
    public Student getObject() throws Exception {
        return new Student(1001, "Akira");
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }
}
