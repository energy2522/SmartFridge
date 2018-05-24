package com.smartfridge.entity;

import com.smartfridge.constants.CameraType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "camera")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Transient
    private String userId;

    @ManyToOne
    private UserFridge userFridge;
}
