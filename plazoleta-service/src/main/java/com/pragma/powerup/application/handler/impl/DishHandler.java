package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.application.dto.response.DishResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.application.mapper.IDishResponseMapper;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;
    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        DishModel dishModel = dishRequestMapper.toDish(dishRequestDto);
        dishServicePort.saveDish(dishModel);
    }

    @Override
    public DishResponseDto getDishById(Long id) {
        DishModel dishModel = dishServicePort.getDishById(id);
        return dishResponseMapper.toResponse(dishModel);
    }

    @Override
    public void updateDish(Long id, DishUpdateRequestDto dishUpdateRequestDto) {
        DishModel dishModel= dishRequestMapper.toDishUpdate(dishUpdateRequestDto);
        dishServicePort.updateDish(id, dishModel);
    }

    @Override
    public void updateEnableDisableDish(Long idDish, Long flag) {
         dishServicePort.updateEnableDisableDish(idDish, flag);
    }

    @Override
    public List<DishResponseDto> getAllDishes() {
        List<DishModel> dishModelList = dishServicePort.getAllDishes();
        if(dishModelList.isEmpty()){
            throw  new NoDataFoundException();
        }
        return dishResponseMapper.toResponseList(dishModelList);
    }

    @Override
    public List<DishResponseDto> findAllByRestauranteId(Long idRestaurante, Integer page, Integer size) {
        List<DishModel> dishModelList = dishServicePort.findAllByRestauranteId(idRestaurante,page,size);
        if(dishModelList.isEmpty()){
            throw new NoDataFoundException();
        }
        return dishResponseMapper.toResponseList(dishModelList);
    }

    @Override
    public void deleleDishById(Long id) {
        dishServicePort.deleteDishById(id);
    }
}
