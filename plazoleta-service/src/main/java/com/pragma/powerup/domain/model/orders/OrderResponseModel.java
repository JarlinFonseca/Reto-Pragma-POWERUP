package com.pragma.powerup.domain.model.orders;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseModel {
    private Long id;
    private Long idCliente;
    private Long idChef;
    private Date fecha;
    private List<OrderDishResponseModel> pedidoPlatos;
}
