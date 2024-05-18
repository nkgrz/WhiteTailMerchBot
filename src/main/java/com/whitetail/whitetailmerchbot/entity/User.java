package com.whitetail.whitetailmerchbot.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
public class User {
    @Id
    @Getter
    @Setter
    @Column(name = "chat_id")
    private Long chatId;

    @Setter
    @Column(name = "user_name")
    private String userName;

}