package com.whitetail.whitetailmerchbot.service;

import java.math.BigDecimal;

public record CartSummary(StringBuilder messagePart, BigDecimal totalPrice, int totalQuantity) {
}
