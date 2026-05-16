package org.strawberries.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.strawberries.orderapi.codegen.types.CreateOrder;
import org.strawberries.orderapi.codegen.types.Order;
import org.strawberries.orderservice.entity.OrderEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    Order toGqlType(OrderEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OrderEntity toEntityFromCreate(CreateOrder input);
}
