package me.nabil.mixed;

/**
 * String 不变性
 *
 * @author zhangbi
 */
public class StringDemo {
    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
        String a = "a";
        System.out.println(a);

        a.replace("a", "b");

        System.out.println(a);

        String c = "ab";
        String a1 = new String("ab");
        String a2 = new String("ab");

        System.out.println(c == a1.intern());
        System.out.println(a2.intern() == a1);
    }
}
