package org.strawberries.cartservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import org.strawberries.cartservice.entity.CartItem;
import org.strawberries.dto.AddItemRequest;
import org.strawberries.dto.CartItemResponse;
import org.strawberries.grpc.CartResponse;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemMapper {
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "discount", ignore = true)
    CartResponse.CartItem toGrpcResponse(CartItem item);

    @Mapping(target = "price", ignore = true)
    @Mapping(target = "priceWithDiscount", ignore = true)
    @Mapping(target = "discount", ignore = true)
    CartItem toEntityFromRestCreate(AddItemRequest request);

    CartItemResponse toRestResponse(CartItem item);

}
