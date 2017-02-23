package me.nabil.demo.quartz.cluster;

/**
 * @author zhangbi
 */
public class ExampleBusinessObject {
    // properties and collaborators

//    @Scheduled(fixedDelay = 500)
    public void doIt() {
        // do the actual work
        System.out.println(this.getClass().getName() + System.currentTimeMillis());
    }
}
