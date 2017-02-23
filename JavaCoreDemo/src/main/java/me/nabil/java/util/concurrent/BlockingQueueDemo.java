package me.nabil.java.util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue 的Demo
 *
 * @author zhangbi
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue q = new ArrayBlockingQueue<Object>(100);
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }

}

class Producer implements Runnable {
    private final BlockingQueue queue;

    private int count = 0;

    Producer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                if (count > 500) {
                    Thread.sleep(500);
                }
                queue.put(produce());
                count++;
            }
        } catch (InterruptedException ex) {

        }
    }

    Object produce() {
        return "reduce:" + System.currentTimeMillis() + ":" + count;
    }
}

class Consumer implements Runnable {
    private final BlockingQueue queue;

    Consumer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(500);
                consume(queue.take());
            }
        } catch (InterruptedException ex) {
        }
    }

    void consume(Object x) {
        System.out.println(Thread.currentThread().getName() + "comume::" + x);
    }
}
