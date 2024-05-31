История ваших заказов:

<#list orders?reverse as order>
    <b><#lt>${order_index + 1}. Заказ №${order.orderId} от ${order.orderDate?string("dd.MM.yyyy")}</b>
    <#list order.orderProducts as item>
        <#lt>${"    "}${item_index + 1}. ${item.productName} (${item.quantity} шт.) - ${item.lotPrice} руб.
    </#list>
    <#lt>Сумма заказа: ${order.total} руб.
    <#lt>Статус заказа: ${order.status}
    <#if order.trackNumber?exists && order.trackNumber?has_content>
        <#lt>Трек номер для отслеживания: ${order.trackNumber}
    <#elseif order.status == "Заказ не оплачен">
        <#lt>Для оплаты заказа свяжитесь с поддержкой
    <#else>
        <#lt>Ожидайте отправки товаров
    </#if>

</#list>