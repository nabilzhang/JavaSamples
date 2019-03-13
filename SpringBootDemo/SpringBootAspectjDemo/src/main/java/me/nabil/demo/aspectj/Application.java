package me.nabil.demo.aspectj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application
 *
 * @author zhangbi
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}