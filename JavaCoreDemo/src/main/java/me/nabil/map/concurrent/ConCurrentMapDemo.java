package me.nabil.map.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConCurrentMapDemo
 *
 * @author zhangbi
 */
public class ConCurrentMapDemo {

    public static void main(String[] args) {
        Map<String, String> map1 = Collections.synchronizedMap(new HashMap<String, String>());
        Map<String, String> map2 = new ConcurrentHashMap<>();
    }
}
