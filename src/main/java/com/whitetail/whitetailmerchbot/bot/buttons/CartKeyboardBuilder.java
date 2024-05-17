package com.whitetail.whitetailmerchbot.bot.buttons;

import com.whitetail.whitetailmerchbot.entity.CartItem;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.BackButton.createBackButton;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButton.createMainButton;
import static com.whitetail.whitetailmerchbot.bot.constants.BotConstantButtonCallback.CALCULATE_DELIVERY_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.BotConstantButtonCallback.CHANGE_BASKET_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.CALCULATE_DELIVERY_TEXT;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.CHANGE_BASKET_TEXT;

public class CartKeyboardBuilder {

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
        // TODO потом указывать цену доставки
        message.append("Доставка: Не учитывается\n\n");

        message.append("Итого: ").append(totalPrice).append(" руб.\n");
        message.append("Все верно?");
        return message.toString();
    }

    public static InlineKeyboardMarkup createCartKeyboard() {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        var calculateDeliveryButton = new InlineKeyboardButton();
        calculateDeliveryButton.setText(CALCULATE_DELIVERY_TEXT);
        calculateDeliveryButton.setCallbackData(CALCULATE_DELIVERY_CALLBACK);
        keyboardRows.add(List.of(calculateDeliveryButton));

        var changeBasketButton = new InlineKeyboardButton();
        changeBasketButton.setText(CHANGE_BASKET_TEXT);
        changeBasketButton.setCallbackData(CHANGE_BASKET_CALLBACK);
        keyboardRows.add(List.of(changeBasketButton));

        keyboardRows.add(List.of(createBackButton(), createMainButton()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

}
