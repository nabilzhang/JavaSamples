package me.nabil.demo.instrumentdemo;

import java.lang.instrument.Instrumentation;

/**
 * 测试
 *
 * @author nabil
 * @date 2019/1/4
 */
public class Agent {

    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行========");
        String className = "java.lang.Thread";
        transformClass(className, inst);
        transformClass(className, inst);
    }

    public static void agentmain(
            String agentArgs, Instrumentation inst) {

        System.out.println("=========agentmain方法执行========");
        String className = "java.lang.Thread";
        transformClass(className, inst);
    }

    private static void transformClass(
            String className, Instrumentation instrumentation) {
        Class<?> targetCls = null;
        ClassLoader targetClassLoader = null;
        // see if we can get the class using forName
        try {
            targetCls = Class.forName(className);
            targetClassLoader = targetCls.getClassLoader();
            transform(targetCls, targetClassLoader, instrumentation);
            return;
        } catch (Exception ex) {
            System.out.println("Class [{}] not found with Class.forName");
        }
        // otherwise iterate all loaded classes and find what we want
        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            if (clazz.getName().equals(className)) {
                targetCls = clazz;
                targetClassLoader = targetCls.getClassLoader();
                transform(targetCls, targetClassLoader, instrumentation);
                return;
            }
        }
        throw new RuntimeException(
                "Failed to find class [" + className + "]");
    }

    private static void transform(
            Class<?> clazz,
            ClassLoader classLoader,
            Instrumentation instrumentation) {
        ThreadTransformer dt = new ThreadTransformer(
                clazz.getName(), classLoader);
        instrumentation.addTransformer(dt, true);
        try {
            instrumentation.retransformClasses(clazz);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Transform failed for: [" + clazz.getName() + "]", ex);
        }
    }

}
