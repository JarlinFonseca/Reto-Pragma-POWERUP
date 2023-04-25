package com.pragma.powerup.domain.spi.feignclients;

import com.pragma.powerup.domain.model.RestaurantModel;

public interface IRestaurantFeingClientPort {

    RestaurantModel getRestaurantByIdPropietario(Long idPropietario);
}
