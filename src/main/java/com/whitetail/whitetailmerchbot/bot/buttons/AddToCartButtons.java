package com.whitetail.whitetailmerchbot.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createBackButton;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createMainButton;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsCallback.ADD_TO_CART_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsCallback.CATALOG_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.ADD_TO_CART;

public class AddToCartButtons {
    public static InlineKeyboardMarkup createAddToCartButtons() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        var buttonAdd = new InlineKeyboardButton();
        buttonAdd.setText(ADD_TO_CART);
        buttonAdd.setCallbackData(ADD_TO_CART_CALLBACK);

        buttons.add(List.of(buttonAdd));
        buttons.add(List.of(createBackButton(CATALOG_CALLBACK), createMainButton()));
        return new InlineKeyboardMarkup(buttons);
    }
}
