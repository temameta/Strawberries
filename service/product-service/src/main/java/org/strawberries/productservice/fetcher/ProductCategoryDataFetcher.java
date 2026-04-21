package org.strawberries.productservice.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.strawberries.productapi.codegen.types.Category;
import org.strawberries.productapi.codegen.types.Product;
import org.strawberries.productservice.service.CategoryService;

@DgsComponent
public record ProductCategoryDataFetcher(CategoryService service) {
    @DgsData(parentType = "Product")
    public Category category(DgsDataFetchingEnvironment dfe) {
        Product product = dfe.getSource();
        return service.findById();
    }
}
