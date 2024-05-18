package com.whitetail.whitetailmerchbot.dao;

import com.whitetail.whitetailmerchbot.entity.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findCartItemsByUserChatId(Long chatId);
    CartItem findCartItemByUserChatIdAndProductProductId(Long chatId, int productId);
}

// TODO 1.
//  Получить все продукты по chat_id (для корзины)
//  2. Изменить количество конкретного продукта в корзине
//  (если больше 0, то оставить иначе удалить, использовать chat_id и product_id)
//  3. delete (использовать chat_id и product_id)