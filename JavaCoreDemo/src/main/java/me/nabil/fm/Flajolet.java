package me.nabil.fm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * https://blog.csdn.net/baimafujinji/article/details/6472658
 *
 * @author zhangbi617
 * @date 2018-08-16
 */
public class Flajolet {
    public static void main(String[] args) {
        List<String> cookies = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            if (i % 3 == 0) {
                String r = UUID.randomUUID().toString();
                cookies.add(r);
                cookies.add(r);
            } else {
                cookies.add(UUID.randomUUID().toString());
            }
        }
//        int bit = (int) (Math.log(new HashSet<>(cookies).size()) / Math.log(2));
        int bit = 10;
        System.out.println(bit);
        int bucketCount = (int) Math.pow(2, bit);

        int[] buckets = new int[bucketCount];
        for (String cookie : cookies) {
            int hash = Objects.hashCode(cookie);
            int index = hash & (bucketCount - 1);
            int bucketHash = hash >> bit;

            int tailcount = tailByte0Count(bucketHash);
            buckets[index] = tailcount > buckets[index] ? tailcount : buckets[index];
        }

        double sum = 0;
        for (int i = 0; i < bucketCount; i++) {
            sum += buckets[i];
        }
        sum = sum / bucketCount;
        double uv = Math.pow(2, sum) * bucketCount * 0.79402;

        System.out.println("pv:" + cookies.size() + ",real:" + new HashSet<>(cookies).size() + ",UV:" + uv);
    }

    private static int tailByte0Count(int i) {
        int count = 0;
        while (i != 0) {
            if ((i & 1) == 0) {
                i = i >> 1;
                count++;
            } else {
                break;
            }
        }
        return count;
    }

}
