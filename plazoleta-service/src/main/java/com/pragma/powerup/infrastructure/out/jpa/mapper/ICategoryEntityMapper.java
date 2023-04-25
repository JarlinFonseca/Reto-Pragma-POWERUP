package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ICategoryEntityMapper {

    //@Mapping(target = "platos", source = "platos")
    CategoryEntity toEntity(CategoryModel categoryModel);
    //@Mapping(target = "platos", source = "platos")
    CategoryModel toCategoryModel(CategoryEntity categoryEntity);
    //@Mapping(target = "platos", source = "platos")
    List<CategoryModel> toCategoryModelList(List<CategoryEntity> categoryEntityList);

}
