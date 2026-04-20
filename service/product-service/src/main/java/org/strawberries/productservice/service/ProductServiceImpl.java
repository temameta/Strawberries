package org.strawberries.productservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.strawberries.productapi.codegen.types.CreateProductInput;
import org.strawberries.productapi.codegen.types.Product;
import org.strawberries.productapi.codegen.types.ProductCollection;
import org.strawberries.productapi.codegen.types.UpdateProductInput;
import org.strawberries.productservice.entity.ProductEntity;
import org.strawberries.productservice.mapper.ProductMapper;
import org.strawberries.productservice.repository.ProductRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    @Override
    public Product create(CreateProductInput input) {
        ProductEntity newProduct = productMapper.toEntityFromCreate(input);
        newProduct.setCategory(categoryService.getEntityById(input.getCategory(), true));
        return productMapper.toGqlType(newProduct);
    }

    @Override
    public ProductCollection findAll(int page, int size, Boolean active) {
        return null;
    }

    @Override
    public Product findById(UUID id, boolean onlyActive) {
        return null;
    }

    @Override
    public Product update(UUID id, UpdateProductInput input) {
        return null;
    }

    @Override
    public Product delete(UUID id) {
        return null;
    }

    @Override
    public Product restore(UUID id) {
        return null;
    }
}
