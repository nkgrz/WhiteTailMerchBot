package com.whitetail.whitetailmerchbot.entity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private User user;

    @Getter
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Getter
    @Column(name = "quantity")
    private Integer quantity;
}