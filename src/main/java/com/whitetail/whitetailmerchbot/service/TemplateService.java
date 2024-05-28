package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.entity.CartItem;
import com.whitetail.whitetailmerchbot.entity.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsText.COST_DELIVERY;

@Service
public class TemplateService {

    private final Configuration freemarkerConfig;

    @Autowired
    public TemplateService(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
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
        model.put("cartItemTotalPrice", calculateTotalPrice(cartItems));

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

    public static BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
