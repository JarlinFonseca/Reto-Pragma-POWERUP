package com.pragma.powerup.domain.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private Long id;
    private Long idCliente;
    private Date fecha;
    private String estado;
    private RestaurantEmployeeModel chef;
    private RestaurantModel restaurante;
}
