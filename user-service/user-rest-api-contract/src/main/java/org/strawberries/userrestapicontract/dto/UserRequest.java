package org.strawberries.userrestapicontract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.strawberries.userrestapicontract.validation.Phone;

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
    String phone,
    @Schema(description = "Роль пользователя", example = "USER")
    Role role
) {}
