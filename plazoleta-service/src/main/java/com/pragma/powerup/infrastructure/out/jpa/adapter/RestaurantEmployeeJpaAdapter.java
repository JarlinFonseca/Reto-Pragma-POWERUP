package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.spi.persistence.IRestaurantEmployeePersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantEmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantEmployeeJpaAdapter implements IRestaurantEmployeePersistencePort {

    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;
    @Override
    public RestaurantEmployeeModel saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        RestaurantEmployeeEntity restaurantEmployeeEntity = restaurantEmployeeRepository.save(restaurantEmployeeEntityMapper.toEntity(restaurantEmployeeModel));
        return restaurantEmployeeEntityMapper.toRestaurantEmployeeModel(restaurantEmployeeEntity);
    }

    @Override
    public List<RestaurantEmployeeModel> getAllRestaurantEmployees() {
        List<RestaurantEmployeeEntity> restaurantEmployeeEntityList = restaurantEmployeeRepository.findAll();
        if(restaurantEmployeeEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return restaurantEmployeeEntityMapper.toRestaurantEmployeeModelList(restaurantEmployeeEntityList);
    }

    @Override
    public RestaurantEmployeeModel findByPersonId(String idEmpleado) {
        Optional<RestaurantEmployeeEntity> restaurantEmployeeEntityOptional = restaurantEmployeeRepository.findByPersonId(idEmpleado);
        RestaurantEmployeeEntity restaurantEmployeeEntity = restaurantEmployeeEntityOptional.orElse(null);
        return restaurantEmployeeEntityMapper.toRestaurantEmployeeModel(restaurantEmployeeEntity);
    }
}
