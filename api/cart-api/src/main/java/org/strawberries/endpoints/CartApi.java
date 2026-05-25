package org.strawberries.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.strawberries.config.CartApiContractConfig;
import org.strawberries.dto.AddItemRequest;
import org.strawberries.dto.CartResponse;
import org.strawberries.dto.DeleteItemRequest;
import org.strawberries.dto.ErrorResponse;

import java.util.UUID;

@Tag(name = "Cart", description = "Управление корзиной пользователя")
@RequestMapping(value = "/api/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public interface CartApi {
    @Operation(
            summary = "Создать корзину для пользователя",
            description = "Создает новую пустую корзину для указанного пользователя",
            security = @SecurityRequirement(name = CartApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Корзина создана")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации запроса", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    CartResponse createNew(
            @Parameter(description = "ID пользователя", required = true, example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Добавить товар в корзину",
            description = "Добавляет указанный товар в корзину пользователя",
            security = @SecurityRequirement(name = CartApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Товар добавлен, возвращается актуальное состояние корзины")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации запроса", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь или товар не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    CartResponse addItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные добавляемого товара", required = true)
            @RequestBody AddItemRequest request
    );

    @Operation(
            summary = "Удалить товар из корзины",
            description = "Удаляет указанный товар из корзины пользователя",
            security = @SecurityRequirement(name = CartApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Товар удалён, возвращается актуальное состояние корзины")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации запроса", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь или товар в корзине не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    CartResponse deleteItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные удаляемого товара", required = true)
            @RequestBody DeleteItemRequest request
    );

    @Operation(
            summary = "Получить корзину пользователя",
            description = "Возвращает текущее содержимое корзины по ID пользователя",
            security = @SecurityRequirement(name = CartApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Корзина найдена")
    @ApiResponse(responseCode = "404", description = "Пользователь или корзина не найдены", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{userId}")
    CartResponse getCart(
            @Parameter(description = "ID пользователя", required = true, example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Очистить корзину пользователя",
            description = "Удаляет все товары из корзины указанного пользователя",
            security = @SecurityRequirement(name = CartApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Корзина очищена")
    @ApiResponse(responseCode = "404", description = "Пользователь или корзина не найдены", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @DeleteMapping("/{userId}")
    CartResponse clearCart(
            @Parameter(description = "ID пользователя", required = true, example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID userId
    );
}