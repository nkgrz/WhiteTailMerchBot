package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.entity.CartItem;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.BackButton.createBackButton;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButton.createMainButton;
import static com.whitetail.whitetailmerchbot.bot.constants.BotConstantButtonCallback.CHANGE_BASKET_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.BotConstantButtonCallback.PLACE_ORDER_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.*;

public class CartBuilder {
    public static String cartItemsToString(List<CartItem> cartItems) {
        StringBuilder message = new StringBuilder();
        message.append("Список ваших товаров в корзине:\n\n");

        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalQuantity = 0;

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            int quantityItem = item.getQuantity();
            BigDecimal priceItem = item.getProduct().getPrice();
            BigDecimal itemTotalPrice = priceItem.multiply(BigDecimal.valueOf(quantityItem));

            message.append(i + 1)
                    .append(". ").append(cartItems.get(i).getProduct().getName())
                    .append(" – ").append(quantityItem).append(" шт.")
                    .append(" – ").append(itemTotalPrice).append(" руб.\n");
            totalPrice = totalPrice.add(itemTotalPrice);
            totalQuantity += quantityItem;
        }

        message.append("\nВсего товаров: ").append(totalQuantity).append(" шт.\n");
        message.append("Стоимость: ").append(totalPrice).append(" руб.\n");
        message.append("Доставка: ").append(COST_DELIVERY).append(" руб.\n\n");
        totalPrice = totalPrice.add(BigDecimal.valueOf(COST_DELIVERY));
        message.append("Итого: ").append(totalPrice).append(" руб.\n");
        message.append("Все верно?");
        return message.toString();
    }
}
