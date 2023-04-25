package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.spi.persistence.ICategoryPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    @Override
    public CategoryModel saveCategory(CategoryModel categoryModel) {
        CategoryEntity categoryEntity = categoryRepository.save(categoryEntityMapper.toEntity(categoryModel));
        return categoryEntityMapper.toCategoryModel(categoryEntity);
    }

    @Override
    public CategoryModel getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntityOptional= categoryRepository.findById(id);
        CategoryEntity categoryEntity = categoryEntityOptional.orElse(null);
        return categoryEntityMapper.toCategoryModel(categoryEntity);
    }

    @Override
    public List<CategoryModel> getAllCategories() {
        List<CategoryEntity> categoryEntityList = (List<CategoryEntity>) categoryRepository.findAll();
        if(categoryEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toCategoryModelList(categoryEntityList);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
