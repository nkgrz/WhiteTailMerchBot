История ваших заказов:

<#list orders as order>
    ${order_index + 1}. Заказ от ${order.orderDate?string("dd.MM.yyyy")}
    <#list order.items as item>
        ${item.product.name} ${item.product.price} руб. (${item.quantity} шт.)
    </#list>
    Сумма заказа: ${order.total} руб.
    Статус заказа: ${order.status}

<#-- Добавить трек номер для отслеживания заказа -->
<#-- order.trackNumber -->

    <#if order_index + 1 != orders?size>
    <#-- Если это не последний заказ, добавим пустую строку -->
        <#lt>
    </#if>
</#list>