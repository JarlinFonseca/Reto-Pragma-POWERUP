package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IDishEntityMapper {

    @Mapping(target = "restauranteId.id", source = "restauranteId.id")
    @Mapping(target = "categoriaId.id", source = "categoriaId.id")
    DishEntity toEntity(DishModel dishModel);

     @Mapping(target = "restauranteId.id", source = "restauranteId.id")
     @Mapping(target = "categoriaId.id", source = "categoriaId.id")
    DishModel toDishModel(DishEntity dishEntity);

    List<DishModel> toDishModelList(List<DishEntity> dishEntityList);
}
