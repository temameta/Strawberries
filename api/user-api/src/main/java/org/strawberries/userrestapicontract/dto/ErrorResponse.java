package org.strawberries.userrestapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Стандартный ответ об ошибке")
public record ErrorResponse(
        @Schema(description = "Код HTTP статуса", example = "404")
        int status,
        @Schema(description = "URI-идентификатор типа ошибки", example = "https://api.example.com/problems/resource-not-found")
        String type,
        @Schema(description = "Краткое название типа ошибки", example = "Ресурс не найден")
        String detail,
        @Schema(description = "URI запроса, приведшего к ошибке", example = "/api/users/42")
        String instance,
        @Schema(description = "Момент возникновения ошибки (UTC)", example = "2026-03-03T10:15:30Z")
        Instant timestamp,
        @Schema(description = "Ошибки по отдельным полям (заполняется только для 400 Bad Request с ошибками валидации)")
        List<FieldError> fieldErrors
) {
    @Schema(description = "Ошибка валидации поля")
    public record FieldError(
            @Schema(description = "Имя невалидного поля", example = "phone")
            String field,
            @Schema(description = "Значение, которое было отклонено", example = "123")
            Object rejectedValue,
            @Schema(description = "Причина отклонения", example = "Некорректный номер телефона")
            String message
    ) {
    }
}
