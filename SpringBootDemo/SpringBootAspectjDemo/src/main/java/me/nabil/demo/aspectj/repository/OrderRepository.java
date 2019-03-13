package me.nabil.demo.aspectj.repository;

import me.nabil.demo.aspectj.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * orderRepository
 *
 * @author nabilzhang
 * @date 2019/3/13
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
}
