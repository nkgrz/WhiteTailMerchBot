package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.dao.CartItemRepository;
import com.whitetail.whitetailmerchbot.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> findCartItemsByUserId(Long chatId) {
        return cartItemRepository.findCartItemsByUserChatId(chatId);
    }
}
