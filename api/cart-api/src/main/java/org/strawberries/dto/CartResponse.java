package org.strawberries.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@Schema(description = "Состояние корзины пользователя")
public class CartResponse {
        @Schema(description = "ID пользователя, которому принадлежит корзина", example = "a3bb189e-8bf9-3888-9912-ace4e6543002")
        UUID userId;

        @Schema(description = "Список товаров в корзине")
        List<CartItemResponse> items;

        @Schema(description = "Итоговая сумма без учёта скидок", example = "150000.00")
        BigDecimal totalPrice;
}