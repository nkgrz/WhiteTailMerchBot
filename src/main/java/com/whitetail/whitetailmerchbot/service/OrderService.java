package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.dao.OrderRepository;
import com.whitetail.whitetailmerchbot.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> findAll(Long chatId) {
        return orderRepository.findOrdersByUserChatId(chatId);
    }
}
