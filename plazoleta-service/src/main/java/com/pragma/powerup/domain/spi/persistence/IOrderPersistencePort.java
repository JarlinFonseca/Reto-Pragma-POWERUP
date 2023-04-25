package com.pragma.powerup.domain.spi.persistence;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;


import java.util.List;


public interface IOrderPersistencePort {
    OrderModel saveOrder(OrderModel orderModel);

     void saveOrderDish(List<OrderDishModel> orderDishModels);

     Boolean existsByIdClienteAndEstado(Long id, String estado);

     List<OrderModel> getAllOrdersWithPagination(Integer page, Integer size, Long restauranteId, String estado);

     List<OrderDishModel> getAllOrdersByPedido(Long pedido_id);

     OrderModel getOrderById(Long id);

     Boolean existsByIdAndEstado(Long id, String estado);
}
