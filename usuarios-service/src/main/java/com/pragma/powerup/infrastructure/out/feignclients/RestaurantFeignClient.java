package com.pragma.powerup.infrastructure.out.feignclients;

import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId = "restaurant",name = "plazoleta-service", url = "localhost:8082/api/v1/restaurant")
public interface RestaurantFeignClient {

    @GetMapping("/restaurantByIdPropietario/{id}")
    RestaurantResponseDto getRestaurantByIdPropietario(@PathVariable(value = "id") Long idPropietario);
}
