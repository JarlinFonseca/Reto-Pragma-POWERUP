package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.OrderResponseDto;
import com.pragma.powerup.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {

    OrderResponseDto toResponse(OrderModel orderModel);
    List<OrderResponseDto> toResponseList(List<OrderModel> orderModels);
    List<OrderResponseDto> toOrderResponseList(List<com.pragma.powerup.domain.model.orders.OrderResponseModel> orderResponseModels);
}
