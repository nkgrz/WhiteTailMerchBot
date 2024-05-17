package com.whitetail.whitetailmerchbot.bot.buttons;

import com.whitetail.whitetailmerchbot.entity.CartItem;
import com.whitetail.whitetailmerchbot.entity.Order;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.BackButton.createBackButton;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButton.createMainButton;

public class OrdersKeyboardBuilder {

    public static String cartItemsToString(List<Order> orders) {
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

    public static InlineKeyboardMarkup createKeyboard(List<CartItem> cartItems) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();


//        var calculateDeliveryButton = new InlineKeyboardButton();
//        calculateDeliveryButton.setText(CALCULATE_DELIVERY_TEXT);
//        calculateDeliveryButton.setCallbackData(CALCULATE_DELIVERY_CALLBACK);
//        keyboardRows.add(List.of(calculateDeliveryButton));
//
//        var changeBasketButton = new InlineKeyboardButton();
//        changeBasketButton.setText(CHANGE_BASKET_TEXT);
//        changeBasketButton.setCallbackData(CHANGE_BASKET_CALLBACK);
//        keyboardRows.add(List.of(changeBasketButton));

        keyboardRows.add(List.of(createBackButton(), createMainButton()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }
}
