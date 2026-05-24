package org.strawberries.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Запрос на добавление товара в корзину")
public record AddItemRequest(
        @Schema(
                description = "ID пользователя, которому принадлежит корзина",
                example = "d290f1ee-6c54-4b01-90e6-d701748f0851",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "ID пользователя не может быть пустым")
        UUID userId,

        @Schema(
                description = "ID добавляемого товара",
                example = "a3bb189e-8bf9-3888-9912-ace4e6543002",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "ID товара не может быть пустым")
        UUID productId,

        @Schema(
                description = "Количество добавляемого товара",
                example = "1",
                minimum = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Min(value = 1, message = "Количество не может быть меньше 1")
        int quantity
) {}