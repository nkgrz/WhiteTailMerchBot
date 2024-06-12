package com.whitetail.whitetailmerchbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderScheduler {

    private final OrderService orderService;

    @Autowired
    OrderScheduler(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(fixedRate = 3600000) // каждый час (60 * 60 * 1000 миллисекунд)
    public void checkAndCancelUnpaidOrders() {
        orderService.cancelUnpaidOrders();
    }
}
