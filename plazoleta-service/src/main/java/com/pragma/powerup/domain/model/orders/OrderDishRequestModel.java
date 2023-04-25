package com.pragma.powerup.domain.model.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishRequestModel {

    private Long idPlatos;
    private Long cantidad;
}
