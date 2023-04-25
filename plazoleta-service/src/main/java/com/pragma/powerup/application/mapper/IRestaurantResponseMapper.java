package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {

    //@Mapping(target = "platos", source = "platos")
    RestaurantResponseDto toResponse(RestaurantModel restaurantModel);

    List<RestaurantResponseDto> toResponseList(List<RestaurantModel> restaurantModelList);


}
