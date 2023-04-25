package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.spi.persistence.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;


public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(CategoryModel categoryModel) {
        categoryPersistencePort.saveCategory(categoryModel);
    }

    @Override
    public CategoryModel getCategoryById(Long id) {
        return categoryPersistencePort.getCategoryById(id);
    }

    @Override
    public List<CategoryModel> getAllCategories() {
        return categoryPersistencePort.getAllCategories();
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryPersistencePort.deleteCategoryById(id);
    }
}
