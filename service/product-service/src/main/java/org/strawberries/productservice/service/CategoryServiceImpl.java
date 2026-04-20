package org.strawberries.productservice.service;

import org.springframework.stereotype.Service;
import org.strawberries.productapi.codegen.types.Category;
import org.strawberries.productapi.codegen.types.CategoryCollection;
import org.strawberries.productapi.codegen.types.CreateCategoryInput;
import org.strawberries.productapi.codegen.types.UpdateCategoryInput;
import org.strawberries.productservice.entity.CategoryEntity;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Override
    public Category create(CreateCategoryInput input) {
        return null;
    }

    @Override
    public CategoryCollection findAll(int page, int size, Boolean active) {
        return null;
    }

    @Override
    public Category findById(UUID id, boolean onlyActive) {
        return null;
    }

    @Override
    public CategoryEntity getEntityById(UUID id, boolean onlyActive) {
        return null;
    }

    @Override
    public Category update(UUID id, UpdateCategoryInput input) {
        return null;
    }

    @Override
    public Category delete(UUID id) {
        return null;
    }

    @Override
    public Category restore(UUID id) {
        return null;
    }
}
