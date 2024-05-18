package com.whitetail.whitetailmerchbot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "products")
public class Product {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Getter
    @Column(name = "name")
    private String name;

    @Getter
    @Column(name = "description")
    private String description;

    @Getter
    @Column(name = "price")
    private BigDecimal price;

    @Setter
    @Getter
    @Column(name = "count")
    private Integer count;

    @Getter
    @Column(name = "image_link")
    private String imageLink;

}
