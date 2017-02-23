package me.nabil.gof.demo.creational;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 单例
 *
 * @author zhangbi
 */
public class SingletonMain {

    public static void main(String args[]) {
        String a = NumberFormat.getCurrencyInstance().format(10f);

        System.out.println(a);

        Map<String, String> map1 = EnumSingleton.INSTANCE.getMap();
        Map<String, String> map2 = EnumSingleton.INSTANCE.getMap();

        // 验证是否单例
        System.out.println(map1 == map2);
    }
}
