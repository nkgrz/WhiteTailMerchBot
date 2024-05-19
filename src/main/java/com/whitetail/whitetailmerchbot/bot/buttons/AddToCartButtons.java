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

    public static InlineKeyboardMarkup createQuantityKeyboard(int productId, int quantity, String callback) {
        return createQuantityKeyboard(productId, quantity, callback, 1);
    }

    public static InlineKeyboardMarkup createQuantityKeyboard(int productId, int quantity, String callback, int start) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

//        Если клавиатура с выбором количества запущена из каталога, будут показаны кнопки от 1 до 10
//        А если из корзины, то есть нужно изменить количество(скорее уменьшить), то от 0 до 9
        for (int i = start; i < start + 10; i++) {

            if ((i + 1 - start) % 5 == 1) {
                keyboardRows.add(new ArrayList<>());
            }
            InlineKeyboardButton button = new InlineKeyboardButton();
            if (quantity >= i) {
                button.setText(i + " шт.");
                button.setCallbackData(callback + productId + "_" + i);
            } else {
                button.setText(" ");
                button.setCallbackData(BLANK_BUTTONS + productId + "_" + i);
            }
            keyboardRows.get(keyboardRows.size() - 1).add(button);

        }
        keyboardRows.add(List.of(createBackButton(PRODUCT_DETAILS_CALLBACK + productId), createMainButton()));
        return new InlineKeyboardMarkup(keyboardRows);
    }
}
