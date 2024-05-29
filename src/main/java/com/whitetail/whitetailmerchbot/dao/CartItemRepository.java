package com.whitetail.whitetailmerchbot.dao;

import com.whitetail.whitetailmerchbot.entity.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findCartItemsByUserChatId(Long chatId);
    CartItem findCartItemByUserChatIdAndProductProductId(Long chatId, int productId);
}
