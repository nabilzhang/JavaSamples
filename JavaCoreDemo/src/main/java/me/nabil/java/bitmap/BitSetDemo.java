package me.nabil.java.bitmap;

import java.util.BitSet;

/**
 * BitSetDemo
 *
 * @author zhangbi
 */
public class BitSetDemo {

    public static void main(String[] args) {
        int[] intArray = {4, 7, 2, 5, 3};
        BitSet bitSet = new BitSet(8);

        for (int i = 0; i < intArray.length; i++) {
            bitSet.set(intArray[i], true);
        }

        System.out.println(bitSet);
    }
}
