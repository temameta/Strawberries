package org.strawberries.productservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.strawberries.productapi.codegen.types.*;
import org.strawberries.productservice.entity.CategoryEntity;
import org.strawberries.productservice.mapper.CategoryMapper;
import org.strawberries.productservice.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public Category create(CreateCategoryInput input) {
        CategoryEntity newCategory = mapper.toEntityFromCreate(input);
        UUID parentId = input.getParent();
        if (parentId == null) newCategory.setParent(null);
        else newCategory.setParent(repository.findById(parentId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Parent category with id=%s not found", parentId))));
        return mapper.toGqlType(repository.save(newCategory));
    }

    @Override
    public CategoryCollection findAll(int page, int size, Boolean active) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryEntity> categoryPage = active == null ? repository.findAll(pageable) : repository.findAllByActive(pageable, active);
        List<Category> content = categoryPage.getContent().stream()
                .map(mapper::toGqlType)
                .toList();
        PageInfo pageInfo = PageInfo.newBuilder()
                .last(categoryPage.isLast())
                .pageNumber(page)
                .totalPages(categoryPage.getTotalPages())
                .pageSize(size)
                .build();
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
