package me.nabil.samples.effectivejava.chapter1;

/**
 * 
 * <h1>第一条:考虑用静态工厂方法代替构造器</h1>
 * 
 * @author zhangbi
 * @date 2014年5月7日下午8:23:10
 */
public class R1 {
    public static void main(String args[]) throws InstantiationException,
            IllegalAccessException {
        R1Temp r1Temp = R1Temp.class.newInstance();
        r1Temp.sayHello();

        // 静态工厂方法引用同一对象
        System.out.println(Boolean.valueOf(true) == Boolean.valueOf(true));

        // 不同对象
        System.out.println(new Boolean(true) == new Boolean(true));
    }
}

class R1Temp {
    public void sayHello() {
        System.out.println("hello");
    }
}