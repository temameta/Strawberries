package org.strawberries.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Товар в корзине")
public record CartItemResponse(
        @Schema(description = "ID позиции в корзине", example = "b5cc5a7e-1b2f-4c3d-9e4a-fc1234567890")
        UUID itemId,

        @Schema(description = "ID товара", example = "a3bb189e-8bf9-3888-9912-ace4e6543002")
        UUID productId,

        @Schema(description = "Количество единиц товара", example = "2")
        int quantity,

        @Schema(description = "Цена товара без скидки", example = "75000.00")
        BigDecimal productPrice,

        @Schema(description = "Скидка на товар, в процентах", example = "50")
        int discount,

        @Schema(description = "Итоговая цена за позицию с учётом скидки", example = "37500.00")
        BigDecimal priceWithDiscount
) {}