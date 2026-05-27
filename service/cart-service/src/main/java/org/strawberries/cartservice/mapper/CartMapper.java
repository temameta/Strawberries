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

    CartResponse toRestResponse(UserCart entity);
}
