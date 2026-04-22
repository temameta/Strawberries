package org.strawberries.productservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.strawberries.productapi.codegen.types.*;
import org.strawberries.productservice.entity.CategoryEntity;
import org.strawberries.productservice.mapper.CategoryMapper;
import org.strawberries.productservice.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public Category create(CreateCategoryInput input) {
        CategoryEntity newCategory = mapper.toEntityFromCreate(input);
        UUID parentId = input.getParent();
        if (parentId == null) newCategory.setParent(null);
        else newCategory.setParent(repository.findById(parentId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Parent category with id=%s not found", parentId))));
        return mapper.toGqlType(repository.save(newCategory));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryCollection findAll(int page, int size, Boolean active) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryEntity> categoryPage = active == null ? repository.findAll(pageable) : repository.findAllByActive(pageable, active);
        List<Category> content = categoryPage.getContent().stream()
                .map(mapper::toGqlType)
                .toList();
        PageInfo pageInfo = PageInfo.newBuilder()
                .last(categoryPage.isLast())
                .pageNumber(categoryPage.getNumber())
                .totalPages(categoryPage.getTotalPages())
                .pageSize(categoryPage.getSize())
                .build();
        return CategoryCollection.newBuilder()
                .content(content)
                .pageInfo(pageInfo)
                .totalElements(categoryPage.getNumberOfElements())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getSubCategories(UUID parentId) {
        List<CategoryEntity> categoryPage = repository.findAllByParent_IdAndActiveTrue(parentId);
        return repository.findAllByParent_IdAndActiveTrue(parentId).stream()
                .map(mapper::toGqlType)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(UUID id, boolean onlyActive) {
        Optional<CategoryEntity> category = onlyActive ? repository.findByIdAndActiveTrue(id) : repository.findById(id);
        if (category.isEmpty()) throw new NoSuchElementException(String.format("Category with id=%s not found", id));
        return mapper.toGqlType(category.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryEntity getEntityById(UUID id) {
        Optional<CategoryEntity> category = repository.findByIdAndActiveTrue(id);
        if (category.isEmpty()) throw new NoSuchElementException(String.format("Category with id=%s not found", id));
        return category.get();
    }

    @Override
    @Transactional
    public Category update(UUID id, UpdateCategoryInput input) {
        CategoryEntity category = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Category with id=%s not found", id)));
        mapper.updateEntity(input, category);
        return mapper.toGqlType(repository.save(category));
    }

    @Override
    @Transactional
    public Category delete(UUID id) {
        CategoryEntity category = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Category with id=%s not found", id)));
        category.setActive(false);
        return mapper.toGqlType(repository.save(category));
    }

    @Override
    @Transactional
    public Category restore(UUID id) {
        CategoryEntity category = repository.findByIdAndActiveFalse(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Category with id=%s not found", id)));
        category.setActive(true);
        return mapper.toGqlType(repository.save(category));
    }
}
