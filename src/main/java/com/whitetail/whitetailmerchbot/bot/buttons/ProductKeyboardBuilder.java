package com.whitetail.whitetailmerchbot.bot.buttons;

import com.whitetail.whitetailmerchbot.entity.Product;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createBackButton;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createMainButton;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsCallback.PRODUCT_DETAILS_CALLBACK;

public class ProductKeyboardBuilder {

    public static InlineKeyboardMarkup createProductKeyboard(List<Product> products) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        for (Product product : products) {
            keyboardRows.add(List.of(createProductButton(product)));
        }

        keyboardRows.add(List.of(createBackButton(), createMainButton()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardButton createProductButton(Product product) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(product.getName());
        button.setCallbackData(PRODUCT_DETAILS_CALLBACK + product.getProductId());
        return button;
    }
}
