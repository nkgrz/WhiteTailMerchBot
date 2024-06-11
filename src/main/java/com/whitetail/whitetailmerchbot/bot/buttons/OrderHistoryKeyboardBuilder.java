package com.whitetail.whitetailmerchbot.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.createMainButton;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsCallback.BLANK_BUTTONS_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsCallback.PAGE_CALLBACK;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.BACK_EMOJI;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.NEXT_EMOJI;

public class OrderHistoryKeyboardBuilder {
    public static InlineKeyboardMarkup createOrderHistoryButtons(int prevPage, int nextPage) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        if (!(prevPage == -1 && nextPage == -1)) {
            var buttonPrev = new InlineKeyboardButton();
            buttonPrev.setText(prevPage == -1 ? " " : BACK_EMOJI);
            buttonPrev.setCallbackData(prevPage == -1 ? BLANK_BUTTONS_CALLBACK : PAGE_CALLBACK + prevPage);

            var buttonNext = new InlineKeyboardButton();
            buttonNext.setText(nextPage == -1 ? " " : NEXT_EMOJI);
            buttonNext.setCallbackData(nextPage == -1 ? BLANK_BUTTONS_CALLBACK : PAGE_CALLBACK + nextPage);

            keyboardRows.add(List.of(buttonPrev, buttonNext));
        }
        keyboardRows.add(List.of(createMainButton()));
        return new InlineKeyboardMarkup(keyboardRows);
    }
}
