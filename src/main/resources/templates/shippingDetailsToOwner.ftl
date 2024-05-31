<b>У вас новый заказ!</b>
${name} <#if username??>(@${username})</#if>
${addressTemplate}
${phoneNumber}

<b>Заказ №${order.orderId}</b> от ${order.orderDate?string("dd.MM.yyyy HH:mm")}
<#list order.orderProducts as item>
    <#lt>${item_index + 1}. ${item.productName} (${item.quantity} шт.) - ${item.lotPrice} руб.
</#list>
<#lt>Сумма заказа: ${order.total} руб.
<#lt>Статус заказа: ${order.status}