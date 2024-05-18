package com.whitetail.whitetailmerchbot.bot.buttons;

import com.whitetail.whitetailmerchbot.entity.Product;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.BackButton.createBackButton;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButton.createMainButton;

public class ProductKeyboardBuilder {

    public static InlineKeyboardMarkup createKeyboard(List<Product> products) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        for (Product product : products) {
            keyboardRows.add(List.of(createProductButton(product)));
        }

        keyboardRows.add(List.of(createBackButton(), createMainButton()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

    // TODO Как делать обработчик???
    private static InlineKeyboardButton createProductButton(Product product) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(product.getName());
        button.setCallbackData("product_" + product.getProductId());
        return button;
    }
}
