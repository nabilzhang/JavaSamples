package me.nabil.demo.instrumentdemo;

import java.lang.instrument.Instrumentation;

/**
 * 测试
 *
 * @author nabil
 * @date 2019/1/4
 */
public class Main {

    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行========");
        System.out.println(agentOps);
    }

    public static void agentmain(
            String agentArgs, Instrumentation inst) {

        System.out.println("=========agentmain方法执行========");
        System.out.println(agentArgs);
    }

}
