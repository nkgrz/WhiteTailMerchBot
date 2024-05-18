package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.entity.Order;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrdersBuilder {

    public static String orderItemsToString(List<Order> orders) {
        StringBuilder message = new StringBuilder();
        message.append("История ваших заказов:\n\n");

        // TODO сортировку по дате бы сделать...
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            message.append(i + 1)
                    .append(". ")
                    .append("Заказ от ")
                    .append(order.getOrderDate().toLocalDateTime().toLocalDate()
                            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                    .append("\n")
//                    TODO изменить временные товары на нормальные
                    .append("Наушники 40 руб.(3 шт)\n").append("Чехол 15 руб.(2 шт.)\n")
                    .append("Сумма заказа: ").append(order.getTotal()).append(" руб.\n")
                    .append("Статус заказа: ").append(order.getStatus())
//                    TODO трек номер для отслеживания заказа
                    .append("\n\n");
        }
        return message.toString();
    }
}
