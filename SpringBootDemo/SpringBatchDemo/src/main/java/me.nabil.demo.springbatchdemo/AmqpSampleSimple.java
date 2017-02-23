package me.nabil.demo.springbatchdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhangbi
 */
public class AmqpSampleSimple {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpSampleSimple.class);

    private AmqpSampleSimple() {
    }

    /**
     * Load the Spring Integration Application Context
     *
     * @param args - command line arguments
     */
    public static void main(final String... args) {

        LOGGER.info("\n========================================================="
                + "\n                                                         "
                + "\n          Welcome to Spring Integration!                 "
                + "\n                                                         "
                + "\n    For more information please visit:                   "
                + "\n    http://www.springsource.org/spring-integration       "
                + "\n                                                         "
                + "\n=========================================================");

        @SuppressWarnings("resource")
        final AbstractApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring-simple-integration.xml");

        context.registerShutdownHook();

        LOGGER.info("\n========================================================="
                + "\n                                                          "
                + "\n    This is the AMQP Sample -                             "
                + "\n                                                          ");

    }
}
