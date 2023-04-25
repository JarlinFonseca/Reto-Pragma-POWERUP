package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEmployeeEntityMapper {

    RestaurantEmployeeEntity toEntity(RestaurantEmployeeModel restaurantEmployeeModel);

    RestaurantEmployeeModel toRestaurantEmployeeModel(RestaurantEmployeeEntity restaurantEmployeeEntity);

    List<RestaurantEmployeeModel> toRestaurantEmployeeModelList(List<RestaurantEmployeeEntity> restaurantEmployeeEntityList);
}
