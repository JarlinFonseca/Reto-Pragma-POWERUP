package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    Optional<RestaurantEntity> findByIdPropietario(Long idPropietario);
}
