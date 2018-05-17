package com.smartfridge.entity;

import com.smartfridge.constants.CameraType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "camera")
@Data
@ToString
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "type")
    private CameraType type;

    @Column(name = "amount")
    private int amount;

    @Column(name = "min")
    private int minAmount = -1;

    @ManyToOne
    private Product product;

    @ManyToOne
    private UserFridge userFridge;
}
