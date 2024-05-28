package com.whitetail.whitetailmerchbot.dao;

import com.whitetail.whitetailmerchbot.entity.OrderProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
List<OrderProduct> findOrderProductByOrderId(Long orderId);
}
