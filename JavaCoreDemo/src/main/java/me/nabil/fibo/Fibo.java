package me.nabil.fibo;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Fibo {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        List<List<Integer>> allList = new ArrayList<List<Integer>>();

        int input_seq = 0;
        int act_number = 0;
        while (input_seq < num) {
            int data = scanner.nextInt();

            List<List<Integer>> listAdd = new ArrayList<List<Integer>>();

            int next_data = -1;
            if (allList.size() == 0) {
                next_data = fibo_next(new ArrayList<Integer>());
                if (data == next_data) {
                    List<Integer> tmpList = new ArrayList<Integer>();
                    tmpList.add(data);
                    allList.add(tmpList);
                }
            } else if (allList.size() == 1) {
                next_data = fibo_next(allList.get(0));
                if (data == next_data) {
                    List<Integer> tmpList = new ArrayList<Integer>(allList.get(0));
                    tmpList.add(data);
                    listAdd.add(tmpList);

                    List<Integer> tmpList2 = new ArrayList<Integer>();
                    tmpList2.add(data);
                    allList.add(tmpList2);
                }
                allList.addAll(listAdd);
            } else {
                for (List<Integer> subList : allList) {
                    next_data = fibo_next(subList);
                    if (data == next_data) {
                        List<Integer> tmpList = new ArrayList<Integer>(subList);
                        listAdd.add(tmpList);
                        subList.add(data);
                    }
                }
                allList.addAll(listAdd);
            }
            input_seq += 1;
        }


        System.out.println(allList.size());
    }

    public static int fibo_next(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return 1;
        } else if (list.size() == 1) {
            return 1;
        } else {
            return list.get(list.size() - 1) + list.get(list.size() - 2);
        }
    }
}
