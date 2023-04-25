package com.pragma.powerup.domain.spi.persistence;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;

import java.util.List;
import java.util.Optional;

public interface IRestaurantEmployeePersistencePort {
    RestaurantEmployeeModel saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel);

    List<RestaurantEmployeeModel> getAllRestaurantEmployees();

    RestaurantEmployeeModel findByPersonId(String idEmpleado);
}
