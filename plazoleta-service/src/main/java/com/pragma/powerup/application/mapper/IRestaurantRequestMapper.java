package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantRequestMapper {

   // @Mapping(target = "dishModelList", source = "dishModelList")
    RestaurantModel toRestaurant(RestaurantRequestDto restaurantRequestDto);


    RestaurantRequestDto toRequest(RestaurantModel restaurantModel);


}
