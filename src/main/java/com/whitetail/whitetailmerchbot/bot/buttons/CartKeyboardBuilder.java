package com.whitetail.whitetailmerchbot.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createBackButton;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createMainButton;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsCallback.CHANGE_BASKET_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsCallback.PLACE_ORDER_CALLBACK;
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
}
