package me.nabil.shutdown.demo;

/**
 * Created by baidu on 15/9/10.
 */
public class ShutdownDemo {

    public static void main(String args[]) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run(){
                System.out.println("exit");
            }
        });

        System.out.println("afakljfkal");
    }
}
