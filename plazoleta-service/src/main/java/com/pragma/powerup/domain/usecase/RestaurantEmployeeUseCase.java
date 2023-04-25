package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.spi.persistence.IRestaurantEmployeePersistencePort;

import java.util.List;

public class RestaurantEmployeeUseCase implements IRestaurantEmployeeServicePort {
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;

    public RestaurantEmployeeUseCase(IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort) {
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
    }

    @Override
    public void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        restaurantEmployeePersistencePort.saveRestaurantEmployee(restaurantEmployeeModel);
    }

    @Override
    public List<RestaurantEmployeeModel> getAllRestaurantEmployees() {
        return restaurantEmployeePersistencePort.getAllRestaurantEmployees();
    }
}
