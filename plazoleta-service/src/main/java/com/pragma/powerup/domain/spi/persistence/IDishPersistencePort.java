package com.pragma.powerup.domain.spi.persistence;

import com.pragma.powerup.domain.model.DishModel;

import java.util.List;

public interface IDishPersistencePort {
    DishModel saveDish(DishModel dishModel);

    DishModel getDishById(Long id);

    List<DishModel> getAllDishes();
    List<DishModel> findAllByRestauranteId(Long idRestaurante, Integer page, Integer size);

    void deleteDishById(Long id);
}
