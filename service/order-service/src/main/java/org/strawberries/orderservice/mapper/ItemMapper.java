package org.strawberries.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.strawberries.orderapi.codegen.types.CreateItem;
import org.strawberries.orderapi.codegen.types.Item;
import org.strawberries.orderservice.entity.ItemEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    Item toGqlType(ItemEntity entity);

    @Mapping(target = "id", ignore = true)
    ItemEntity toEntityFromCreate(CreateItem input);
}
