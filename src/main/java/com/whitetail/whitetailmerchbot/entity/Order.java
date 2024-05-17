package com.whitetail.whitetailmerchbot.entity;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private User user;

    @Getter
    @Column(name = "total")
    private BigDecimal total;

    @Getter
    @Column(name = "order_date")
    private Timestamp orderDate;

    @Getter
    @Column(name = "status")
    private String status;

    @Getter
    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product")
    private List<String> products;

}