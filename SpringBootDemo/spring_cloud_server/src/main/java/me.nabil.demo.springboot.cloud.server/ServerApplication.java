package me.nabil.demo.springboot.cloud.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * configServer
 *
 * @author zhangbi
 */
@Configuration
@EnableAutoConfiguration
@RestController
@EnableConfigServer()
@EnableDiscoveryClient
public class ServerApplication {

//    @Value("${test.a.name}")
    String name = "World";

    @RequestMapping("/")
    public String home() {
        return "Hello " + name;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}