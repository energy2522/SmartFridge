package com.smartfridge.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_fridge")
@Data
public class UserFridge {

    @Id
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Fridge fridge;
}
