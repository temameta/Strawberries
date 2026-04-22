package org.strawberries.productservice.mapper;

import org.mapstruct.*;
import org.strawberries.productapi.codegen.types.CreateProductInput;
import org.strawberries.productapi.codegen.types.Product;
import org.strawberries.productapi.codegen.types.UpdateProductInput;
import org.strawberries.productservice.entity.ProductEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "category.id", source = "category.id")
    @Mapping(target = "category.products", ignore = true)
    Product toGqlType(ProductEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "category", ignore = true)
    ProductEntity toEntityFromCreate(CreateProductInput input);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntity(UpdateProductInput input, @MappingTarget ProductEntity entity);
}
