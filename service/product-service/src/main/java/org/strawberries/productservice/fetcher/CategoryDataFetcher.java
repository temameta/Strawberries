package org.strawberries.productservice.fetcher;

import com.netflix.graphql.dgs.*;
import org.strawberries.productapi.codegen.types.*;
import org.strawberries.productservice.service.CategoryService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@DgsComponent
public record CategoryDataFetcher(CategoryService service) {
    @DgsQuery
    public Category category(@InputArgument UUID id) {
        return service.findById(id, true);
    }

    @DgsQuery
    public CategoryCollection categories(
            @InputArgument CategoryFilter filter,
            @InputArgument Integer page,
            @InputArgument Integer size) {
        page = Objects.requireNonNullElse(page, 0);
        size = Objects.requireNonNullElse(size, 20);
        return service.findAll(page, size, true);
    }

    @DgsMutation
    public Category createCategory(@InputArgument CreateCategoryInput input) {
        return service.create(input);
    }

    @DgsMutation
    public Category updateCategory(
            @InputArgument UUID id,
            @InputArgument UpdateCategoryInput input
            ) {
        return service.update(id, input);
    }

    @DgsMutation
    public Category deleteCategory(@InputArgument UUID id) {
        return service.delete(id);
    }

    @DgsMutation
    public Category restoreCategory(@InputArgument UUID id) {
        return service.restore(id);
    }
}
