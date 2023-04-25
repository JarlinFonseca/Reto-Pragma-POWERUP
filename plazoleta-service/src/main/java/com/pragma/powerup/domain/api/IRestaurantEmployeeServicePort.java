package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;


import java.util.List;

public interface IRestaurantEmployeeServicePort {

    void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel);

    List<RestaurantEmployeeModel> getAllRestaurantEmployees();
}
