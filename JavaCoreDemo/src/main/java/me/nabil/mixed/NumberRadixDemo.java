package me.nabil.mixed;

/**
 * 进制转换
 *
 * @author yuanshan
 * @date 2020/10/7
 */
public class NumberRadixDemo {
    public static void main(String[] args) {
        System.out.println(Long.parseLong("894028402aks", 32));
        System.out.println(Long.toString(4829048092L, 32));
    }
}
