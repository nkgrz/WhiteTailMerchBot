package com.whitetail.whitetailmerchbot.bot.buttons;

import com.whitetail.whitetailmerchbot.entity.CartItem;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createBackButton;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createMainButton;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsCallback.*;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.CHANGE_BASKET_TEXT;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.PLACE_ORDER_TEXT;

public class CartKeyboardBuilder {
    public static InlineKeyboardMarkup createCartKeyboard() {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

//       TODO сделать кнопку для подсчета доставки

        var changeBasketButton = new InlineKeyboardButton();
        changeBasketButton.setText(CHANGE_BASKET_TEXT);
        changeBasketButton.setCallbackData(CHANGE_BASKET_CALLBACK);
        keyboardRows.add(List.of(changeBasketButton));

        var placeOrderButton = new InlineKeyboardButton();
        placeOrderButton.setText(PLACE_ORDER_TEXT);
        placeOrderButton.setCallbackData(PLACE_ORDER_CALLBACK);
        keyboardRows.add(List.of(placeOrderButton));

        keyboardRows.add(List.of(createBackButton(), createMainButton()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup changeCartKeyboard(List<CartItem> cartItems) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            var changeCartButton = new InlineKeyboardButton();
            changeCartButton.setText((i + 1) + ". " + cartItem.getProduct().getName());
            changeCartButton.setCallbackData(CHANGE_QUANTITY_FROM_CART_CALLBACK + cartItem.getProduct().getProductId());
            keyboardRows.add(List.of(changeCartButton));
        }

        keyboardRows.add(List.of(createBackButton(CART_CALLBACK), createMainButton()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

}
