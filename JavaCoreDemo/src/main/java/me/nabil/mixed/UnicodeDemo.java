package me.nabil.mixed;

/**
 * Unicode, codePoint
 *
 * @author nabil
 * @date 2020/10/7
 */
public class UnicodeDemo {


    public static void main(String[] args) {
        System.out.println(Character.toChars(32534));
        System.out.println(Character.toChars(128515));

        System.out.println(sub("\uD83D\uDC8B"));
        System.out.println(sub("a\uD83D\uDC8Bdfsfs"));
        System.out.println(sub("\uD83D\uDC8B\uD83D\uDC8B\uD83D\uDC8Bbfsf\uD83D\uDC8B"));

        System.out.println("\uD83D\uDC8B".codePointCount(0, Math.min("\uD83D\uDC8B".length(), 3)));
        System.out.println("\uD83D\uDC8B".length());
    }

    private static String sub(String str) {
        return str.substring(str.offsetByCodePoints(0, str.codePointCount(0, 0))
                , str.offsetByCodePoints(0, str.codePointCount(0, Math.min(str.length(), 3)))) + "...";
    }
}
