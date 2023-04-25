package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantEmployeeRepository extends JpaRepository<RestaurantEmployeeEntity, Long> {

    Optional<RestaurantEmployeeEntity> findByPersonId(String idEmpleado);
}
