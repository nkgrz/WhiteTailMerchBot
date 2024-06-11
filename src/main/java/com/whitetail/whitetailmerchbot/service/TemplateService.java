package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.entity.CartItem;
import com.whitetail.whitetailmerchbot.entity.Order;
import com.whitetail.whitetailmerchbot.entity.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingAddress;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.COST_DELIVERY;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.MAX_NUMBER_ORDERS_PER_PAGE;

@Service
public class TemplateService {

    private final Configuration freemarkerConfig;

    @Autowired
    public TemplateService(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    public static BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String createCartMessage(List<CartItem> cartItems) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        BigDecimal totalPrice = calculateTotalPrice(cartItems);
        BigDecimal totalPriceWithDelivery = totalPrice.add(BigDecimal.valueOf(COST_DELIVERY));
        int totalQuantity = cartItems.stream().mapToInt(CartItem::getQuantity).sum();

        model.put("cartItems", cartItems);
        model.put("totalPrice", totalPrice);
        model.put("totalQuantity", totalQuantity);
        model.put("costDelivery", COST_DELIVERY);
        model.put("totalPriceWithDelivery", totalPriceWithDelivery);

        Template template = freemarkerConfig.getTemplate("cartItemsTemplate.ftl");

        try (StringWriter out = new StringWriter()) {
            template.process(model, out);
            return out.toString();
        }
    }

    public String createChangeCartMessage(List<CartItem> cartItems) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();

        model.put("cartItems", cartItems);

        Template template = freemarkerConfig.getTemplate("changeCartItemsTemplate.ftl");

        try (StringWriter out = new StringWriter()) {
            template.process(model, out);
            return out.toString();
        }
    }

    public String createProductDetailsMessage(Product product) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();

        model.put("product", product);

        Template template = freemarkerConfig.getTemplate("productDetailsTemplate.ftl");

        try (StringWriter out = new StringWriter()) {
            template.process(model, out);
            return out.toString();
        }
    }

    public String createOrdersMessage(List<Order> orders, int page) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();

        model.put("orders", orders);
        model.put("index", page * MAX_NUMBER_ORDERS_PER_PAGE);

        Template template = freemarkerConfig.getTemplate("ordersTemplate.ftl");

        try (StringWriter out = new StringWriter()) {
            template.process(model, out);
            return out.toString();
        }
    }

    public String createOrderDescription(List<CartItem> cartItems) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        BigDecimal totalPrice = calculateTotalPrice(cartItems);
        BigDecimal totalPriceWithDelivery = totalPrice.add(BigDecimal.valueOf(COST_DELIVERY));
        int totalQuantity = cartItems.stream().mapToInt(CartItem::getQuantity).sum();

        model.put("totalQuantity", totalQuantity);
        model.put("totalPrice", totalPrice);
        model.put("costDelivery", COST_DELIVERY);
        model.put("totalPriceWithDelivery", totalPriceWithDelivery);

        Template template = freemarkerConfig.getTemplate("orderDescription.ftl");

        try (StringWriter out = new StringWriter()) {
            template.process(model, out);
            return out.toString();
        }
    }

    public String createShippingAddressMessage(ShippingAddress shippingAddress) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("state", shippingAddress.getState());
        model.put("city", shippingAddress.getCity());
        model.put("streetLine1", shippingAddress.getStreetLine1() == null ? "" : shippingAddress.getStreetLine1());
        model.put("streetLine2", shippingAddress.getStreetLine2() == null ? "" : shippingAddress.getStreetLine2() + ", ");
        model.put("postCode", shippingAddress.getPostCode());
        Template template = freemarkerConfig.getTemplate("shippingAddress.ftl");

        try (StringWriter out = new StringWriter()) {
            template.process(model, out);
            return out.toString();
        }
    }

    public String createMessageToOwner(String name, String username, String addressTemplate, String phoneNumber, Order order) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("username", username);
        model.put("addressTemplate", addressTemplate);
        model.put("phoneNumber", phoneNumber);
        model.put("order", order);
        Template template = freemarkerConfig.getTemplate("shippingDetailsToOwner.ftl");
        try (StringWriter out = new StringWriter()) {
            template.process(model, out);
            return out.toString();
        }
    }
}
