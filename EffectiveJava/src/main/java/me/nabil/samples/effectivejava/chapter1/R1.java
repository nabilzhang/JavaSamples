package me.nabil.samples.effectivejava.chapter1;

import java.util.EnumSet;


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
        /**
         * ---------------------------------------
         */
        // 静态工厂方法引用同一对象
        System.out.println(Boolean.valueOf(true) == Boolean.valueOf(true));

        // 不同对象
        System.out.println(new Boolean(true) == new Boolean(true));

        /**
         * ---------------------------------------
         * 静态工厂与构造器不同的第三大优势在于,他们可以返回原返回类型的任何子类型的对象
         */
        //
        EnumSet<Enuma> set = EnumSet.noneOf(Enuma.class);
        System.out.println(set);

        set = EnumSet.allOf(Enuma.class);
        System.out.println(set);

    }
}

enum Enuma {
    a, b
}

class R1Temp {
    public void sayHello() {
        System.out.println("hello");
    }
}