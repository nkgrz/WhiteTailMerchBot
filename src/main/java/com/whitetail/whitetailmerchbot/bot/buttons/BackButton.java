package com.whitetail.whitetailmerchbot.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.constants.BotConstantButtonCallback.BACK_TO_MENU_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.*;

public class BackButton {
    // TODO переделать кнопку, чтобы принимала параметр куда назад
    public static InlineKeyboardButton createBackButton() {
        var buttonBack = new InlineKeyboardButton();
        buttonBack.setText(BACK_BUTTON_TEXT);
        // TODO изменить на назад а не в меню
        buttonBack.setCallbackData(BACK_TO_MENU_CALLBACK);
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
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(List.of(createBackButton(), createMainButton()));
        return new InlineKeyboardMarkup(keyboardRows);
    }
}
