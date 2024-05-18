package com.whitetail.whitetailmerchbot.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private User user;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Getter
    @Setter
    @Column(name = "quantity")
    private Integer quantity;
}