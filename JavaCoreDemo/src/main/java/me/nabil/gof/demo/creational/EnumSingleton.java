package me.nabil.gof.demo.creational;


import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbi
 */
public enum EnumSingleton {
    INSTANCE();

    private Map<String, String> map;

    private EnumSingleton() {
        map = new HashMap<>();
        map.put("1", "1");
    }

    public Map<String, String> getMap() {
        return map;
    }
}
