package me.nabil.stream;

import java.util.ArrayList;
import java.util.List;

public class StreamMain {

    public static void main(String[] args) {
        List<FooData> datas = initData();
        datas.stream()
                .filter(a -> a.getId() % 10 == 0)
                .forEach((a) -> {
                    System.out.println(a);
                    System.out.println(a.getGroupName());
                });

        System.out.println("-------------------");

        datas.stream()
                .filter(a -> a.getId() % 10 == 0)
                .reduce((a, b) -> a = b)
                .map(a -> {
                    System.out.println(a);
                    System.out.println(a.getName());
                    return null;
                });

    }

    private static List<FooData> initData() {
        List<FooData> fooDataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            fooDataList.add(new FooData(i, "名称" + i, "组" + i % 11));
        }
        return fooDataList;
    }
}
