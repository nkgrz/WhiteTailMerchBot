package com.whitetail.whitetailmerchbot.service;

import com.whitetail.whitetailmerchbot.dao.CartItemRepository;
import com.whitetail.whitetailmerchbot.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartService(CartItemRepository cartItemRepository,
                       UserService userService,
                       ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public void addToCart(long chatId, int productId, int quantity) {
        CartItem existingCartItem = cartItemRepository.findCartItemByUserChatIdAndProductProductId(chatId, productId);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(userService.getUser(chatId));
            cartItem.setProduct(productService.getProductById(productId));
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    public void setQuantity(long chatId, int productId, int quantity) {
        if (quantity <= 0) {
            deleteFromCart(chatId, productId);
        } else {
            CartItem cartItem = cartItemRepository.findCartItemByUserChatIdAndProductProductId(chatId, productId);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    public void deleteFromCart(long chatId, int productId) {
        CartItem cartItem = cartItemRepository.findCartItemByUserChatIdAndProductProductId(chatId, productId);
        cartItemRepository.delete(cartItem);
    }

    public List<CartItem> findCartItemsByUserId(Long chatId) {
        return cartItemRepository.findCartItemsByUserChatId(chatId);
    }
}
