package me.nabil.map;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap Main
 *
 * @author zhangbi
 */
public class HashMapDemoMain {
    public static void main(String[] args) {

        Map<MapKey, MapItem> map = new HashMap<>();
        map.put(new MapKey("a"), new MapItem("a"));
        map.put(new MapKey("a"), new MapItem("a"));

        System.out.println("map:" + map);

        System.out.println(map.get(new MapKey("a")));
    }
}
