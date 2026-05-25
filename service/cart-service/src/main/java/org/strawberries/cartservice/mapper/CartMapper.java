package org.strawberries.cartservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import org.strawberries.cartservice.entity.UserCart;
import org.strawberries.dto.CartResponse;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CartItemMapper.class})
public interface CartMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CartResponse toRestResponse(UserCart entity);
}
