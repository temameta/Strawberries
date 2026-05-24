package org.strawberries.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Запрос на удаление товара в корзину")
public record DeleteItemRequest(
        @Schema(
                description = "ID пользователя, которому принадлежит корзина",
                example = "d290f1ee-6c54-4b01-90e6-d701748f0851",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "ID пользователя не может быть пустым")
        UUID userId,
        @Schema(
                description = "ID удаляемого товара",
                example = "a3bb189e-8bf9-3888-9912-ace4e6543002",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "ID товара не может быть пустым")
        UUID productId
) {
}
