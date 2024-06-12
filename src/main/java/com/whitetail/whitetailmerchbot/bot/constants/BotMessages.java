package com.whitetail.whitetailmerchbot.bot.constants;

public interface BotMessages {
    String WELCOME_MESSAGE = "Добро пожаловать в наш магазин!";
    String CATALOG_MESSAGE = "Каталог товаров";
    String HELP_MESSAGE = "Вопросы о товарах задавать @annasalnikova1989\nЕсли что-то работает не так, как ожидалось – @storm256";
    String ADD_TO_CART_TEXT = "Выберите количество товара. Доступно: ";
    String CART_IS_EMPTY = "Корзина пуста";
    String ORDERS_IS_EMPTY = "Заказов пока не было";
    String MAIN_MENU_TEXT = "Главное меню";
    String PRODUCT_ADD_TO_CART_TEXT = "Товар успешно добавлен в корзину";
    String ORDER_CANCELLED_MESSAGES = "К сожалению, ваш заказ был отменен, " +
            "так как время ожидания оплаты истекло. Пожалуйста, попробуйте оформить заказ заново.\n";
    String ORDER_SUCCESSFULLY_PAID = "Ваш заказ успешно оплачен!";
}
