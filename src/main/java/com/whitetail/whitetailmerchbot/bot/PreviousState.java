package com.whitetail.whitetailmerchbot.bot;

public enum PreviousState {
        MAIN_MENU,
        CATALOG,
        BASKET,
}

//    сделать переменную, которая будет загружаться из БД(chatID, state)
//    в случаях case "/menu": сделать установку состояния,
//    а там где if (callbackData.startsWith("cart_item_") устанавливать state на каталог/корзину и все
//    в кнопке назад смотреть предыдущее состояние и переключаться на него, при этом менять PreviousState на MAIN_MENU

//     case "/menu":
//          sendMenu(chatId);
//          setPreviousState(MAIN_MENU); // установить состояние главное мею
//          break;
//     case "/help":
//          sendMessage(chatId, HELP_MESSAGE);
//          setPreviousState(MAIN_MENU); // установить состояние главное мею
//          break;

