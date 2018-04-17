package com.smartfridge.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "camera")
@Data
public class Camera {

    @Id
    private int id;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Fridge fridge;
}
