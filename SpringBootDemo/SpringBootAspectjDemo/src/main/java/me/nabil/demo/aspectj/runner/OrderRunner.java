package me.nabil.demo.aspectj.runner;

import me.nabil.demo.aspectj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * OrderRunner
 *
 * @author nabilzhang
 * @date 2019/3/13
 */
@Component
public class OrderRunner implements CommandLineRunner {

    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... strings) throws Exception {
        orderService.generateOrder();
    }
}
