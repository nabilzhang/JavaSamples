package me.nabil.demo.aspectj.service.impl;

import me.nabil.demo.aspectj.domain.Order;
import me.nabil.demo.aspectj.domain.OrderGoods;
import me.nabil.demo.aspectj.repository.OrderGoodsRepository;
import me.nabil.demo.aspectj.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * TransactionMgr
 *
 * @author nabilzhang
 * @date 2019/3/13
 */
@Component
public class TransactionMgr {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderGoodsRepository orderGoodsRepository;

    @Transactional
    public void saveInDb(Order order, OrderGoods orderGoods) {
        order = orderRepository.save(order);
        orderGoods.setOrderId(order.getId());
        orderGoodsRepository.save(orderGoods);
    }
}
