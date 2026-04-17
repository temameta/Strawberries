package org.strawberries.userapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.strawberries.userapi.validation.Phone;

@Schema(description = "Запрос на создание или полное обновление пользователя")
public record UserRequest(
    @Schema(description = "Email пользователя", example = "example@gmail.com")
    @Email(message = "Некорректный email")
    @Size(max = 255, message = "Email не может превышать 255 символов")
    String email,
    @Schema(description = "Имя пользователя", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 255, message = "Имя не может превышать 255 символов")
    String firstName,
    @Schema(description = "Номер телефона пользователя", example = "+79998886655", requiredMode = Schema.RequiredMode.REQUIRED)
    @Phone
    @NotBlank(message = "Номер телефона не может быть пустым")
    String phone,
    @Schema(description = "Пароль пользователя", example = "123456")
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(max = 255, message = "Пароль не может превышать 255 символов")
    String password,
    @Schema(description = "Роль пользователя", example = "USER")
    Role role
) {}
