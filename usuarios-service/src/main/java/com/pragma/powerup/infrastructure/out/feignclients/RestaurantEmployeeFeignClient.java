package com.pragma.powerup.infrastructure.out.feignclients;

import com.pragma.powerup.application.dto.request.RestaurantEmployeeRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(contextId = "restaurantEmployee",name = "plazoleta-service", url = "localhost:8082/api/v1/restaurantEmployee")
public interface RestaurantEmployeeFeignClient {

     @PostMapping("/")
     void saveRestaurantEmployee(RestaurantEmployeeRequestDto restaurantEmployeeRequestDto);




}
