package me.nabil.demo.jsonpath;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * JsonPath demo
 *
 * @author zhangbi
 * @date 2017/6/28
 * @since 1.0.0
 */
public class Demo {

    public static void main(String[] args) throws IOException {
        allPage();
        System.out.println("-------");

        ReadContext ctx = JsonPath.parse(new File("/Users/zhangbi/Desktop/response-export1"));
        List<Integer> allId1 = ctx.read("$.result.datas[0].datas[*].id");

        ReadContext ctx2 = JsonPath.parse(new File("/Users/zhangbi/Desktop/response-export2"));
        List<Integer> allId2 = ctx2.read("$.result.datas[0].datas[*].id");

        ReadContext ctx3 = JsonPath.parse(new File("/Users/zhangbi/Desktop/response-export3"));
        List<Integer> allId3 = ctx3.read("$.result.datas[0].datas[*].id");


        Set<Integer> idSet = new HashSet<>();
        idSet.addAll(allId1);
        idSet.addAll(allId2);
        idSet.addAll(allId3);
        System.out.println(idSet.size());

    }

    private static void allPage() throws IOException {
        ReadContext ctx = JsonPath.parse(new File("/Users/zhangbi/Desktop/response-export"));
        List<Integer> allId = ctx.read("$.result.datas[0].datas[*].id");
        System.out.println(allId);
        System.out.println(allId.size());

        Map<Integer, Integer> map = new TreeMap<>();

        Set<Integer> idSet = new HashSet<>();
        for (Integer id : allId) {
            idSet.add(Integer.valueOf(String.valueOf(id).trim()));

            if (map.containsKey(id)) {
                map.put(id, map.get(id) + 1);
            } else {
                map.put(id, 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }


        System.out.println(idSet.size());

        System.out.println(allId.removeAll(idSet));

        System.out.println(allId);
    }
}
