package com.whitetail.whitetailmerchbot.dao;

import com.whitetail.whitetailmerchbot.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findOrdersByUserChatId(Long chatId);
}
