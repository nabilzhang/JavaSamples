package me.nabil.java.util.seviceloader;

/**
 * Hello Service implement
 *
 * @author zhangbi
 */
public class HelloServiceImpl1 implements HelloService {
    @Override
    public String say() {
        return this.toString();
    }
}
