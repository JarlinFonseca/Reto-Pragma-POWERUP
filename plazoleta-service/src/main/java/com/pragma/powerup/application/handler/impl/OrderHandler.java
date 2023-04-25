package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.mapper.IOrderRequestMapper;
import com.pragma.powerup.application.mapper.IOrderResponseMapper;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.orders.OrderRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler  implements IOrderHandler {

    private final IOrderServicePort orderServicePort;

    private final IOrderRequestMapper orderRequestMapper;

    private final IOrderResponseMapper orderResponseMapper;

    @Override
    public void saveOrder(OrderRequestDto orderRequest) {
        OrderRequestModel orderRequestModel= orderRequestMapper.toOrderRequestModel(orderRequest);
           orderServicePort.saveOrder(orderRequestModel);
    }

    @Override
    public List<OrderResponseDto> getAllOrdersWithPagination(Integer page, Integer size, String estado) {
        return orderResponseMapper.toOrderResponseList(orderServicePort.getAllOrdersWithPagination(page,size,estado));
    }

    @Override
    public void takeOrderAndUpdateStatus(Long idOrder, String estado) {
        orderServicePort.takeOrderAndUpdateStatus(idOrder,estado);
    }

    @Override
    public void updateAndNotifyOrderReady(Long idOrder) {
        orderServicePort.updateAndNotifyOrderReady(idOrder);
    }

    @Override
    public void deliverOrder(Long idOrder, String pin) {
        orderServicePort.deliverOrder(idOrder,pin);
    }

    @Override
    public void cancelOrder(Long idOrder) {
        orderServicePort.cancelOrder(idOrder);
    }

}
