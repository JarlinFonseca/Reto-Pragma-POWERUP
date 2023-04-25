package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderResponseDto;

import java.util.List;

public interface IOrderHandler {
    void saveOrder(OrderRequestDto orderRequest);
    List<OrderResponseDto> getAllOrdersWithPagination(Integer page, Integer size, String estado);

    void takeOrderAndUpdateStatus(Long idOrder, String estado);

    void updateAndNotifyOrderReady(Long idOrder);

    void deliverOrder(Long idOrder, String pin);

    void cancelOrder(Long idOrder);

}
