package me.nabil.demo.aspectj.service.impl;

import me.nabil.demo.aspectj.domain.Order;
import me.nabil.demo.aspectj.domain.OrderGoods;
import me.nabil.demo.aspectj.repository.OrderGoodsRepository;
import me.nabil.demo.aspectj.repository.OrderRepository;
import me.nabil.demo.aspectj.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * order service
 *
 * @author nabilzhang
 * @date 2019/3/13
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderGoodsRepository orderGoodsRepository;

    @Override
    public void generateOrder() {
        LOGGER.info("generating order");
        Order order = new Order();
        order.setName("test");

        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setGoodsId(1);

        saveInDb(order, orderGoods);
        LOGGER.info("generating order finish");
    }

    @Transactional
    public void saveInDb(Order order, OrderGoods orderGoods) {
        order = orderRepository.save(order);
        orderGoods.setOrderId(order.getId());
        orderGoodsRepository.save(orderGoods);
    }
}
