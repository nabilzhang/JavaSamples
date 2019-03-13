package me.nabil.demo.aspectj.repository;

import me.nabil.demo.aspectj.domain.OrderGoods;
import org.springframework.data.repository.CrudRepository;

/**
 * OrderGoodsRepository
 *
 * @author nabilzhang
 * @date 2019/3/13
 */
public interface OrderGoodsRepository extends CrudRepository<OrderGoods, Integer> {
}
