<#import "cartItems.ftl" as ci>
Список ваших товаров в корзине:

<@ci.cartItemBlock cartItems=cartItems />

Всего товаров: ${totalQuantity} шт.
Стоимость: ${totalPrice} руб.
Доставка: ${costDelivery} руб.

<b>Итого: ${totalPriceWithDelivery} руб.
Все верно?</b>
