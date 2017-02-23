package me.nabil.java.util.seviceloader;

import java.util.ServiceLoader;

/**
 * 一个SPI的demo
 *
 * @author zhangbi
 */
public class ServiceLoaderDemo {

    public static void main(String[] args) {
        ServiceLoader<HelloService> loader = ServiceLoader.load(HelloService.class);

        for (HelloService helloService : loader) {
            System.out.println(helloService.say());
        }
    }
}
