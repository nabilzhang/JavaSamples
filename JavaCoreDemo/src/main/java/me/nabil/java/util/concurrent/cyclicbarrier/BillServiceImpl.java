package me.nabil.java.util.concurrent.cyclicbarrier;

import java.util.Random;

/**
 * @author zhangbi
 */
public class BillServiceImpl implements BillService {

    @Override
    public int bill(String code) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextInt();
    }
}
