package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.CategoryRequestDto;
import com.pragma.powerup.application.dto.response.CategoryResponseDto;
import com.pragma.powerup.application.handler.ICategoryHandler;
import com.pragma.powerup.application.mapper.ICategoryRequestMapper;
import com.pragma.powerup.application.mapper.ICategoryResponseMapper;
import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;
    @Override
    public void saveCategory(CategoryRequestDto categoryRequestDto) {
        CategoryModel categoryModel= categoryRequestMapper.toCategory(categoryRequestDto);
        categoryServicePort.saveCategory(categoryModel);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        CategoryModel categoryModel = categoryServicePort.getCategoryById(id);
        return categoryResponseMapper.toResponse(categoryModel);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<CategoryModel> categoryModelList = categoryServicePort.getAllCategories();
        if(categoryModelList.isEmpty()){
            throw new NoDataFoundException();
        }
        return categoryResponseMapper.toResposeList(categoryModelList);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryServicePort.deleteCategoryById(id);
    }
}
