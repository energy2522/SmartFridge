package com.smartfridge.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_fridge")
@Getter
@ToString
@Setter
public class UserFridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Fridge fridge;

    @OneToMany(mappedBy = "userFridge")
    private List<Camera> cameras;
}
