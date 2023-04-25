package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {

    @Mapping(target = "restauranteId.id", source = "restauranteId")
    @Mapping(target = "categoriaId.id", source = "categoriaId")
    DishModel toDish(DishRequestDto dishRequestDto);


    DishModel toDishUpdate(DishUpdateRequestDto dishUpdateRequestDto);



   /* @Mapping(target = "restauranteId", source = "restauranteId.id")
    @Mapping(target = "categoriaId", source = "categoriaId.id")
    DishRequestDto toRequest(DishModel dishModel);*/
}
