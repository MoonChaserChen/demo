package ink.akira.duplicateRequest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by akira on 2019/2/22.
 */
@SpringBootApplication
@MapperScan("ink.akira.duplicateRequest.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
