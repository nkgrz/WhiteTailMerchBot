package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.dao.OrderRepository;
import com.whitetail.whitetailmerchbot.entity.CartItem;
import com.whitetail.whitetailmerchbot.entity.Order;
import com.whitetail.whitetailmerchbot.entity.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.constants.OtherConstants.ORDER_STATUS_CANCELED;
import static com.whitetail.whitetailmerchbot.bot.constants.OtherConstants.ORDER_STATUS_UNPAID;
import static com.whitetail.whitetailmerchbot.service.TemplateService.calculateTotalPrice;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Slice<Order> findAll(Long chatId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());
        return orderRepository.findOrdersByUserChatId(chatId, pageable);
    }

    public Order findOrderByOrderId(Long orderId) {
        return orderRepository.findOrderByOrderId(orderId);
    }

    public Order createOrder(Long chatId, List<CartItem> cartItems) {
        Order order = new Order();
        order.setUser(userService.getUser(chatId));
        order.setTotal(calculateTotalPrice(cartItems));
        order.setOrderDate(Timestamp.from(Instant.now()));
        order.setStatus(ORDER_STATUS_UNPAID);
        order.setOrderProducts(getOrderProducts(cartItems, order));
        orderRepository.save(order);
        return order;
    }

    private List<OrderProduct> getOrderProducts(List<CartItem> cartItems, Order order) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (CartItem item : cartItems) {
            var currentProduct = new OrderProduct();
            currentProduct.setOrderId(order.getOrderId());
            currentProduct.setProductId(item.getProduct().getProductId());
            currentProduct.setProductName(item.getProduct().getName());
            currentProduct.setQuantity(item.getQuantity());
            currentProduct.setLotPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderProducts.add(currentProduct);
        }
        return orderProducts;
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = findOrderByOrderId(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

    public void cancelUnpaidOrders() {
        Timestamp oneHourAgo = Timestamp.valueOf(LocalDateTime.now().minusHours(1));
        List<Order> ordersToCancel = orderRepository.findOrdersByStatusAndOrderDateBefore(ORDER_STATUS_UNPAID, oneHourAgo);
        for (Order order : ordersToCancel) {
            order.setStatus(ORDER_STATUS_CANCELED);
            orderRepository.save(order);
            productService.updateProductQuantity(order);
        }
    }
}