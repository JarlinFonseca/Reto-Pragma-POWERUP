package com.pragma.powerup.domain.model.orders;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestModel {
    private List<OrderDishRequestModel> platos;
    private Long  resturanteId;
}
