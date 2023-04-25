package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantPaginationResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.application.mapper.IRestaurantPaginationResponseMapper;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.application.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;

    private final IRestaurantRequestMapper restaurantRequestMapper;

    private final IRestaurantResponseMapper restaurantResponseMapper;

    private final IRestaurantPaginationResponseMapper restaurantPaginationResponseMapper;


    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        RestaurantModel restaurantModel = restaurantRequestMapper.toRestaurant(restaurantRequestDto);
        restaurantServicePort.saveRestaurant(restaurantModel);
    }

    @Override
    public RestaurantResponseDto getRestaurantById(Long id) {
        RestaurantResponseDto restaurantResponseDto = restaurantResponseMapper.toResponse(restaurantServicePort.getRestaurantById(id));
        return restaurantResponseDto;
    }

    @Override
    public RestaurantResponseDto getRestaurantByIdPropietario(Long id_propietario) {
        RestaurantResponseDto restaurantResponseDto = restaurantResponseMapper.toResponse(restaurantServicePort.getRestaurantByIdPropietario(id_propietario));
        return restaurantResponseDto;
    }

    @Override
    public List<RestaurantResponseDto> getAllRestaurants() {
        return restaurantResponseMapper.toResponseList(restaurantServicePort.getAllRestaurants());
    }

    @Override
        public List<RestaurantPaginationResponseDto> getRestaurantsWithPagination(Integer page, Integer size) {
        return restaurantPaginationResponseMapper.toResponseListPagination(restaurantServicePort.getRestaurantsWithPagination(page,size));
    }

    @Override
    public void deleteRestaurantById(Long id) {
        restaurantServicePort.deleteRestaurantById(id);

    }
}
