package com.pragma.powerup.domain.spi.persistence;

import com.pragma.powerup.domain.model.CategoryModel;

import java.util.List;

public interface ICategoryPersistencePort {
    CategoryModel saveCategory(CategoryModel categoryModel);

    CategoryModel getCategoryById(Long id);

    List<CategoryModel> getAllCategories();

    void deleteCategoryById(Long id);
}
