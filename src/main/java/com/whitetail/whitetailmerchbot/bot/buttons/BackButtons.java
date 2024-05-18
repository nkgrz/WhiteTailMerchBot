package com.whitetail.whitetailmerchbot.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.constants.BotConstantButtonCallback.BACK_TO_MENU_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.BACK_BUTTON_TEXT;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.MAIN_BUTTON_TEXT;

public class BackButtons {

    public static InlineKeyboardButton createBackButton() {
        return createBackButton(BACK_TO_MENU_CALLBACK);
    }

    public static InlineKeyboardButton createBackButton(String callbackData) {
        var buttonBack = new InlineKeyboardButton();
        buttonBack.setText(BACK_BUTTON_TEXT);
        buttonBack.setCallbackData(callbackData);
        return buttonBack;
    }

    public static InlineKeyboardButton createMainButton() {
        var buttonBack = new InlineKeyboardButton();
        buttonBack.setText(MAIN_BUTTON_TEXT);
        buttonBack.setCallbackData(BACK_TO_MENU_CALLBACK);
        return buttonBack;
    }

    // из кнопки делать InlineKeyboardMarkup(типа список со списком кнопок, иногда нужно именно это)
    public static InlineKeyboardMarkup buttonToMarkup(InlineKeyboardButton btn) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(List.of(btn));
        return new InlineKeyboardMarkup(keyboardRows);
    }

    public static InlineKeyboardMarkup createBackAndMainButtons() {
        return createBackAndMainButtons(BACK_TO_MENU_CALLBACK);
    }

    public static InlineKeyboardMarkup createBackAndMainButtons(String callbackData) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(List.of(createBackButton(callbackData), createMainButton()));
        return new InlineKeyboardMarkup(keyboardRows);
    }
}
