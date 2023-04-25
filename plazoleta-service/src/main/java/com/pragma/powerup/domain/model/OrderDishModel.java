package com.pragma.powerup.domain.model;

import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishModel {
    private Long id;
    private OrderModel pedido;
    private DishModel plato;
    private String cantidad;
}
