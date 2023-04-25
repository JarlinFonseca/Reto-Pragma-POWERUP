package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CategoryRequestDto;
import com.pragma.powerup.application.dto.response.CategoryResponseDto;

import java.util.List;

public interface ICategoryHandler {

    void saveCategory(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto getCategoryById(Long id);
    List<CategoryResponseDto> getAllCategories();
    void deleteCategoryById(Long id);

}
