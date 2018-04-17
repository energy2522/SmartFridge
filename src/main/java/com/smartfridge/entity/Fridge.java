package com.smartfridge.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fridge")
@Data
public class Fridge {

    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "high")
    private double high;

    @Column(name = "width")
    private double width;

    @Column(name = "camera_count")
    private int amountCamera;

    @Column(name = "price")
    private double price;

}
