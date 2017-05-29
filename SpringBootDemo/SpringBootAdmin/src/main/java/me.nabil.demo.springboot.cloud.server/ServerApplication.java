package me.nabil.demo.springboot.cloud.server;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot Admin
 *
 * @author zhangbi
 */
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}