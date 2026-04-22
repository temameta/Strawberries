package org.strawberries.productservice.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.strawberries.productapi.codegen.types.*;
import org.strawberries.productservice.service.ProductService;

import java.util.Objects;
import java.util.UUID;

@DgsComponent
public record ProductDataFetcher(ProductService service) {
    @DgsQuery
    public Product product(@InputArgument UUID id) {
        return service.findById(id, true);
    }

    @DgsQuery
    public ProductCollection products(
            @InputArgument ProductFilter filter,
            @InputArgument Integer page,
            @InputArgument Integer size) {
        page = Objects.requireNonNullElse(page, 0);
        size = Objects.requireNonNullElse(size, 20);
        return service.findAll(page, size, true, null);
    }

    @DgsMutation
    public Product createProduct(@InputArgument CreateProductInput input) {
        return service.create(input);
    }

    @DgsMutation
    public Product updateProduct(
            @InputArgument UUID id,
            @InputArgument UpdateProductInput input) {
        return service.update(id, input);
    }

    @DgsMutation
    public Product deleteProduct(@InputArgument UUID id) {
        return service.delete(id);
    }

    @DgsMutation
    public Product restoreProduct(@InputArgument UUID id) {
        return service.restore(id);
    }
}
