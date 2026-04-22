package org.strawberries.productservice.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.strawberries.productapi.codegen.types.Category;
import org.strawberries.productservice.service.CategoryService;

import java.util.List;
import java.util.Objects;

@DgsComponent
public record CategorySubCategoriesDataFetcher(CategoryService service) {
    @DgsData(parentType = "Category")
    public List<Category> subcategories(DgsDataFetchingEnvironment dfe) {
        Category parent = Objects.requireNonNull(dfe.getSource(), "Source category cannot be null");
        return service.getSubCategories(parent.getId());
    }
}
