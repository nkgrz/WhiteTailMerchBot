package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.dao.ShippingDetailsRepository;
import com.whitetail.whitetailmerchbot.entity.ShippingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingDetailsService {
    private final ShippingDetailsRepository sdRepository;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ShippingDetailsService(ShippingDetailsRepository sdRepository, UserService userService, OrderService orderService) {
        this.sdRepository = sdRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

    public void newShippingDetails(Long chatId, Long orderId, String name, String phone, String shippingAddress) {
        ShippingDetails shippingDetails = new ShippingDetails();
        shippingDetails.setUser(userService.getUser(chatId));
        shippingDetails.setOrder(orderService.findOrderByOrderId(orderId));
        shippingDetails.setName(name);
        shippingDetails.setPhoneNumber(phone);
        shippingDetails.setShippingAddress(shippingAddress);
        sdRepository.save(shippingDetails);
    }
}
