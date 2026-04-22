package org.strawberries.productservice.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.InputArgument;
import org.strawberries.productapi.codegen.types.Category;
import org.strawberries.productapi.codegen.types.ProductCollection;
import org.strawberries.productservice.service.ProductService;

import java.util.Objects;

@DgsComponent
public record CategoryProductsDataFetcher(ProductService service) {
    @DgsData(parentType = "Category")
    public ProductCollection products(
            DgsDataFetchingEnvironment dfe,
            @InputArgument Integer page,
            @InputArgument Integer size) {
        page = Objects.requireNonNullElse(page, 0);
        size = Objects.requireNonNullElse(size, 20);
        Category category = Objects.requireNonNull(dfe.getSource(), "Source category cannot be null");
        return service.findAll(page, size, true, category.getId());
    }
}
