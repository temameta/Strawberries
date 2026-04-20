package org.strawberries.userapi.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.strawberries.userapi.config.UserApiContractConfig;
import org.strawberries.userapi.dto.*;

import java.util.UUID;

@Tag(name = "Users", description = "Управление пользователями")
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserApi {
    @Operation(
            summary = "Список пользователей",
            description = "Возвращает постраничный список пользователей с HATEOAS-ссылками",
            security = @SecurityRequirement(name = UserApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Список пользователей")
    @GetMapping
    PagedModel<EntityModel<UserResponse>> getAllUsers(
            @Parameter(description = "Номер страницы (0..N", example = "0")
            @RequestParam(defaultValue = "0")
            int page,
            @Parameter(description = "Размер страницы", example = "20")
            @RequestParam(defaultValue = "20")
            int size
    );

    @Operation(
            summary = "Получить пользователя по ID",
            security = @SecurityRequirement(name = UserApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{id}")
    EntityModel<UserResponse> getUserById(
            @Parameter(description = "ID пользователя", required = true, example = "1")
            @PathVariable
            UUID id
    );

    @Operation(
            summary = "Создать пользователя",
            security = @SecurityRequirement(name = UserApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "201", description = "Пользователь создан")
    @ApiResponse(responseCode = "400", description = "Ошибка создания пользователя", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<UserResponse>> createUser(@Valid @RequestBody UserRequest request);

    @Operation(
            summary = "Полное обновление пользователя",
            description = "Заменяет все поля пользователя",
            security = @SecurityRequirement(name = UserApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Пользователь обновлен")
    @ApiResponse(responseCode = "400", description = "Ошибка обновления пользователя", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    EntityModel<UserResponse> updateUser(
            @Parameter(description = "ID пользователя", required = true, example = "1")
            @PathVariable
            UUID id,
            @Valid @RequestBody UserRequest request
    );

    @Operation(
            summary = "Частичное обновление пользователя",
            description = "Заменяет непустые поля пользователя",
            security = @SecurityRequirement(name = UserApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Пользователь обновлен")
    @ApiResponse(responseCode = "400", description = "Ошибка обновления пользователя", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    EntityModel<UserResponse> patchUser(
            @Parameter(description = "ID пользователя", required = true, example = "1")
            @PathVariable
            UUID id,
            @Valid @RequestBody UserPatchRequest request
    );

    @Operation(
            summary = "Удалить пользователя по ID",
            security = @SecurityRequirement(name = UserApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Пользователь удален")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @DeleteMapping("/{id}")
    EntityModel<UserResponse> deleteUserById(
            @Parameter(description = "ID пользователя", required = true, example = "1")
            @PathVariable
            UUID id
    );

    @Operation(
            summary = "Восстановить пользователя по ID",
            security = @SecurityRequirement(name = UserApiContractConfig.SECURITY_SCHEME_BEARER)
    )
    @ApiResponse(responseCode = "200", description = "Пользователь восстановлен")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping("/{id}")
    EntityModel<UserResponse> restoreUserById(
            @Parameter(description = "ID пользователя", required = true, example = "1")
            @PathVariable
            UUID id
    );
}
