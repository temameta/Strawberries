package org.strawberries.productservice.service;

import org.strawberries.productapi.codegen.types.Category;
import org.strawberries.productapi.codegen.types.CategoryCollection;
import org.strawberries.productapi.codegen.types.CreateCategoryInput;
import org.strawberries.productapi.codegen.types.UpdateCategoryInput;
import org.strawberries.productservice.entity.CategoryEntity;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category create(CreateCategoryInput input);

    CategoryCollection findAll(int page, int size, Boolean active);

    List<Category> getSubCategories(UUID parentId);

    Category findById(UUID id, boolean onlyActive);

    CategoryEntity getEntityById(UUID id);

    Category update(UUID id, UpdateCategoryInput input);

    Category delete(UUID id);

    Category restore(UUID id);
}
