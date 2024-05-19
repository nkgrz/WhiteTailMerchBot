package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.entity.CartItem;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.COST_DELIVERY;

public class CartBuilder {

    public static String cartItemsToString(List<CartItem> cartItems) {
        CartSummary summary = buildCartItemsSummary(cartItems);
        StringBuilder message = summary.messagePart();
        BigDecimal totalPrice = summary.totalPrice();
        int totalQuantity = summary.totalQuantity();

        message.append("\nВсего товаров: ").append(totalQuantity).append(" шт.\n");
        message.append("Стоимость: ").append(formatPrice(totalPrice));
        message.append("Доставка: ").append(COST_DELIVERY).append("\n\n");
        totalPrice = totalPrice.add(BigDecimal.valueOf(COST_DELIVERY));
        message.append("Итого: ").append(formatPrice(totalPrice));
        message.append("Все верно?");
        return message.toString();
    }

    public static String formatPrice(BigDecimal price) {
        DecimalFormat priceFormat = new DecimalFormat("##,###");
        return priceFormat.format(price) + " руб.\n";
    }

    public static CartSummary buildCartItemsSummary(List<CartItem> cartItems) {
        StringBuilder messagePart = new StringBuilder();
        messagePart.append("Список ваших товаров в корзине:\n\n");
        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalQuantity = 0;

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            int quantityItem = item.getQuantity();
            BigDecimal priceItem = item.getProduct().getPrice();
            BigDecimal itemTotalPrice = priceItem.multiply(BigDecimal.valueOf(quantityItem));

            messagePart.append(i + 1)
                    .append(". ").append(item.getProduct().getName())
                    .append(" – ").append(quantityItem).append(" шт.")
                    .append(" – ").append(formatPrice(itemTotalPrice));

            totalPrice = totalPrice.add(itemTotalPrice);
            totalQuantity += quantityItem;
        }
        return new CartSummary(messagePart, totalPrice, totalQuantity);
    }

    public static String changeCartItemsToString(List<CartItem> cartItems) {
        StringBuilder message = buildCartItemsSummary(cartItems).messagePart();
        message.append("\nВыберите товар который хотите изменить: ");
        return message.toString();
    }
}
