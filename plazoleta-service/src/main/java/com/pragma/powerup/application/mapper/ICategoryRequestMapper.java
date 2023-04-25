package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.CategoryRequestDto;
import com.pragma.powerup.domain.model.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {

    CategoryModel toCategory(CategoryRequestDto categoryRequestDto);

    CategoryRequestDto toRequest(CategoryModel categoryModel);
}
