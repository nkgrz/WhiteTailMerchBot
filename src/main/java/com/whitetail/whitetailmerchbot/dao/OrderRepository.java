package com.whitetail.whitetailmerchbot.dao;

import com.whitetail.whitetailmerchbot.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Slice<Order> findOrdersByUserChatId(Long chatId, Pageable pageable);
    Order findOrderByOrderId(Long id);
}
