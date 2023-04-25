package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderDishEntityMapper {
    OrderDishEntity toEntity(OrderDishModel orderDishModel);
    OrderDishModel toOrderModel(OrderDishEntity orderDishEntity);

    List<OrderDishModel> toOrderDishModelList(List<OrderDishEntity> orderDishEntities);
}
