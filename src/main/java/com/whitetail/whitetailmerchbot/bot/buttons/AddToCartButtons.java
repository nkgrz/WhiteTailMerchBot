package com.whitetail.whitetailmerchbot.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createBackButton;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createMainButton;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsCallback.*;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.ADD_TO_CART_TEXT;

public class AddToCartButtons {
    public static InlineKeyboardMarkup createAddToCartButtons(int productId) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        var buttonAdd = new InlineKeyboardButton();
        buttonAdd.setText(ADD_TO_CART_TEXT);
        buttonAdd.setCallbackData(ADD_TO_CART_CALLBACK + productId);

        buttons.add(List.of(buttonAdd));
        buttons.add(List.of(createBackButton(CATALOG_CALLBACK), createMainButton()));
        return new InlineKeyboardMarkup(buttons);
    }

    public static InlineKeyboardMarkup createQuantityKeyboard(int productId, int quantity) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {

            if (i % 5 == 1) {
                keyboardRows.add(new ArrayList<>());
            }
            InlineKeyboardButton button = new InlineKeyboardButton();
            if (quantity >= i) {
                button.setText(i + " шт.");
                button.setCallbackData(QUANTITY_CALLBACK + productId + "_" + i);
            } else {
                button.setText(" ");
                button.setCallbackData(LACK_OF_QUANTITY_CALLBACK + productId + "_" + i);
            }
            keyboardRows.get(keyboardRows.size() - 1).add(button);

        }
        keyboardRows.add(List.of(createBackButton(CATALOG_CALLBACK), createMainButton()));
        return new InlineKeyboardMarkup(keyboardRows);
    }
}
