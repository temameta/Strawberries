package org.strawberries.productservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.strawberries.productapi.codegen.types.*;
import org.strawberries.productservice.entity.ProductEntity;
import org.strawberries.productservice.mapper.ProductMapper;
import org.strawberries.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryService categoryService;

    @Transactional
    @Override
    public Product create(CreateProductInput input) {
        ProductEntity newProduct = mapper.toEntityFromCreate(input);
        newProduct.setCategory(categoryService.getEntityById(input.getCategory()));
        return mapper.toGqlType(newProduct);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductCollection findAll(int page, int size, Boolean active, UUID categoryId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> productPage;
        if (categoryId == null)
            productPage = active == null ? repository.findAll(pageable) : repository.findAllByActive(pageable, active);
        else
            productPage = active == null ? repository.findAllByCategory_Id(pageable, categoryId) : repository.findAllByCategory_IdAndActive(pageable, categoryId, active);
        List<Product> content = productPage.getContent().stream()
                .map(mapper::toGqlType)
                .toList();
        PageInfo pageInfo = PageInfo.newBuilder()
                .last(productPage.isLast())
                .pageNumber(productPage.getNumber())
                .totalPages(productPage.getTotalPages())
                .pageSize(productPage.getSize())
                .build();
        return ProductCollection.newBuilder()
                .content(content)
                .pageInfo(pageInfo)
                .totalElements(productPage.getNumberOfElements())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(UUID id, boolean onlyActive) {
        Optional<ProductEntity> product = onlyActive ? repository.findByIdAndActiveTrue(id) : repository.findById(id);
        if (product.isEmpty()) throw new NoSuchElementException(String.format("Product with id %s not found", id));
        return mapper.toGqlType(product.get());
    }

    @Override
    @Transactional
    public Product update(UUID id, UpdateProductInput input) {
        ProductEntity product = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Product with id %s not found", id)));
        mapper.updateEntity(input, product);
        return mapper.toGqlType(repository.save(product));
    }

    @Override
    @Transactional
    public Product delete(UUID id) {
        ProductEntity product = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Product with id %s not found", id)));
        product.setActive(false);
        return mapper.toGqlType(repository.save(product));

    }

    @Override
    @Transactional
    public Product restore(UUID id) {
        ProductEntity product = repository.findByIdAndActiveFalse(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Product with id %s not found", id)));
        product.setActive(true);
        return mapper.toGqlType(repository.save(product));
    }
}
