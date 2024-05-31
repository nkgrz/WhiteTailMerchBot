<#macro cartItemBlock cartItems>
    <#list cartItems as cartItem>
        <#lt>${cartItem_index + 1}. ${cartItem.product.name} (${cartItem.quantity} шт.) – ${cartItem.product.price*cartItem.quantity} руб.
    </#list>
</#macro>
