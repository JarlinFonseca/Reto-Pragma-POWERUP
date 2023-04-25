package com.pragma.powerup.infrastructure.out.feignclients.adapter;

import com.pragma.powerup.application.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.powerup.application.mapper.IRestaurantEmployeeRequestMapper;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.spi.feignclients.IRestaurantEmployeeFeignClientPort;
import com.pragma.powerup.infrastructure.out.feignclients.RestaurantEmployeeFeignClient;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class RestaurantEmployeeFeignAdapter implements IRestaurantEmployeeFeignClientPort {

    private  final RestaurantEmployeeFeignClient restaurantEmployeeFeignClient;

    private final IRestaurantEmployeeRequestMapper restaurantEmployeeRequestMapper;
    @Override
    public void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        RestaurantEmployeeRequestDto restaurantEmployeeRequestDto = restaurantEmployeeRequestMapper.toRequest(restaurantEmployeeModel);
         restaurantEmployeeFeignClient.saveRestaurantEmployee(restaurantEmployeeRequestDto);
    }

}
