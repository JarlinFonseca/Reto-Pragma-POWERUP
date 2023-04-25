package com.pragma.powerup.domain.spi.feignclients;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;

import java.util.List;

public interface IRestaurantEmployeeFeignClientPort {

    void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel);

    //List<RestaurantEmployeeModel> getAllRestaurantEmployees();


}
