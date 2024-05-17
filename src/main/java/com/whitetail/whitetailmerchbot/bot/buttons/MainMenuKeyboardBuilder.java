package com.whitetail.whitetailmerchbot.bot.buttons;

import com.whitetail.whitetailmerchbot.bot.constants.BotConstantButtonCallback;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.*;

public class MainMenuKeyboardBuilder {
    public static InlineKeyboardMarkup createMenuKeyboard() {

        var buttonCatalog = new InlineKeyboardButton();
        buttonCatalog.setText(CATALOG_BUTTON_TEXT);
        buttonCatalog.setCallbackData(BotConstantButtonCallback.CATALOG_CALLBACK);

        var buttonBasket = new InlineKeyboardButton();
        buttonBasket.setText(BASKET_BUTTON_TEXT);
        buttonBasket.setCallbackData(BotConstantButtonCallback.BASKET_CALLBACK);

        var buttonHistory = new InlineKeyboardButton();
        buttonHistory.setText(HISTORY_BUTTON_TEXT);
        buttonHistory.setCallbackData(BotConstantButtonCallback.HISTORY_CALLBACK);

        var buttonSupport = new InlineKeyboardButton();
        buttonSupport.setText(HELP_BUTTON_TEXT);
        buttonSupport.setUrl(BotConstantButtonCallback.SUPPORT_URL);

        var buttonReviews = new InlineKeyboardButton();
        buttonReviews.setText(REVIEWS_BUTTON_TEXT);
        buttonReviews.setUrl(BotConstantButtonCallback.REVIEWS_URL);

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(List.of(buttonCatalog, buttonBasket));
        keyboardRows.add(List.of(buttonHistory, buttonSupport));
        keyboardRows.add(List.of(buttonReviews));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }
}
