package me.nabil.jmx.demo;

import java.lang.management.*;
import javax.management.*;

/**
 * Created by baidu on 15/9/14.
 */
public class HelloAgent {

    public static void main(String args[]) throws Exception{
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        ObjectName name = new ObjectName("me.nabil.jmx.demo:type=Hello");

        Hello mbean = new Hello();

        mbs.registerMBean(mbean, name);

        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }


}
