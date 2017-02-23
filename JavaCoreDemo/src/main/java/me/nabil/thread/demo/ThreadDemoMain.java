package me.nabil.thread.demo;

/**
 * @author zhangbi
 */
public class ThreadDemoMain extends Thread {
    public static void main(String[] args) {
        new ThreadDemoMain().start();
    }

    public void run() {
        int i = 0;
        while (true) {
            System.out.println(i++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
