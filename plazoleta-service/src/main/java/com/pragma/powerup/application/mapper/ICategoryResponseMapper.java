package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.CategoryResponseDto;
import com.pragma.powerup.domain.model.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryResponseMapper {
   // @Mapping(target = "platos", source = "platos")
    CategoryResponseDto toResponse(CategoryModel categoryModel);

    //@Mapping(target = "platos", source = "platos")
    List<CategoryResponseDto> toResposeList(List<CategoryModel> categoryModelList);
}
