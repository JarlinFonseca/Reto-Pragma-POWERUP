package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.RestaurantEmployeeResponseDto;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEmployeeResponseMapper {

    RestaurantEmployeeResponseDto toResponse(RestaurantEmployeeModel restaurantEmployeeModel);

    List<RestaurantEmployeeResponseDto> toResponseList(List<RestaurantEmployeeModel> restaurantEmployeeModelList);


}
