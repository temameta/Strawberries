package org.strawberries.productservice.mapper;

import org.mapstruct.*;
import org.strawberries.productapi.codegen.types.Category;
import org.strawberries.productapi.codegen.types.CreateCategoryInput;
import org.strawberries.productapi.codegen.types.UpdateCategoryInput;
import org.strawberries.productservice.entity.CategoryEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "products", ignore = true)
    Category toGqlType(CategoryEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "parent", ignore = true)
    CategoryEntity toEntityFromCreate(CreateCategoryInput input);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    void updateEntity(UpdateCategoryInput input, @MappingTarget CategoryEntity entity);
}
