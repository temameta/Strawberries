package org.strawberries.productservice.service;

import org.strawberries.productapi.codegen.types.CreateProductInput;
import org.strawberries.productapi.codegen.types.Product;
import org.strawberries.productapi.codegen.types.ProductCollection;
import org.strawberries.productapi.codegen.types.UpdateProductInput;

import java.util.UUID;

public interface ProductService {
    Product create(CreateProductInput input);
    ProductCollection findAll(int page, int size, Boolean active);
    Product findById(UUID id, boolean onlyActive);
    Product update(UUID id, UpdateProductInput input);
    Product delete(UUID id);
    Product restore(UUID id);
}
