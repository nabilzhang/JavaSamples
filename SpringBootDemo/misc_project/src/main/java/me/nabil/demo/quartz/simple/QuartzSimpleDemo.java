package me.nabil.demo.quartz.simple;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author zhangbi
 */
public class QuartzSimpleDemo {
    public static void main(String[] args) {

        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
