package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Long> {

    List<OrderDishEntity> findByPedido_Id(Long pedido_id);
}
