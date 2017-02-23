package me.nabil.java.lang.object.classloaderdemo;

/**
 * Classloader demo
 *
 * @author zhangbi
 */
public class ClassLoaderDemo {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = ClassLoaderDemo.class.getClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());

        System.out.println(System.getProperty("testarg", "a"));
        System.out.println(System.getProperties());

        new A("a");
        new A("a");

        System.out.println("Thread.currentThread().getContextClassLoader:"
                + Thread.currentThread().getContextClassLoader());

        System.out.println(ClassLoader.getSystemClassLoader());

        Class aClass = Class.forName("me.nabil.java.lang.object.classloaderdemo.A");

        A a = (A) aClass.newInstance();
    }


}

class A {
    public A(String a) {
        System.out.println("construct" + this.getClass().getClassLoader());

    }
}