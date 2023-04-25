package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.application.dto.response.DishResponseDto;

import java.util.List;

public interface IDishHandler {

    void saveDish(DishRequestDto dishRequestDto);
    DishResponseDto getDishById(Long id);
    void updateDish(Long id, DishUpdateRequestDto dishUpdateRequestDto);

    void updateEnableDisableDish(Long idDish, Long flag);
    List<DishResponseDto> getAllDishes();
    List<DishResponseDto> findAllByRestauranteId(Long idRestaurante, Integer page, Integer size);
    void deleleDishById(Long id);
}
