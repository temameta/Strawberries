package org.strawberries.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "Состояние корзины пользователя")
public record CartResponse(
        @Schema(description = "ID корзины", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
        UUID cartId,

        @Schema(description = "ID пользователя, которому принадлежит корзина", example = "a3bb189e-8bf9-3888-9912-ace4e6543002")
        UUID userId,

        @Schema(description = "Список товаров в корзине")
        List<CartItemResponse> items,

        @Schema(description = "Итоговая сумма без учёта скидок", example = "150000.00")
        BigDecimal totalPrice
) {}