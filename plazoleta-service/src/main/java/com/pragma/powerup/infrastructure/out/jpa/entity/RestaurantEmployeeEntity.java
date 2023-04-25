package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "empleados_restaurante")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEmployeeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "empleado_restaurante_id", nullable = false)
    private Long id;

    @Column(name = "resturante_id", nullable = false)
    private String restaurantId;

    @Column(name = "empleado_id", nullable = false)
    private String personId;
}
