package org.strawberries.userrestapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "users", itemRelation = "user")
@Schema(description = "Информация о пользователе")
public class UserResponse {
    @Schema(description = "ID пользователя", example = "1")
    private final Long id;
    @Schema(description = "Email пользователя", example = "example@gmail.com")
    private final String email;
    @Schema(description = "Имя пользователя", example = "Иван")
    private final String firstName;
    @Schema(description = "Номер телефона пользователя", example = "+79998886655")
    private final String phone;
    @Schema(description = "Роль пользователя", example = "USER")
    private final Role role;
    @Schema(description = "Активен пользователь или нет (неактивен = удален)", example = "true")
    private final boolean isActive;
    @Schema(description = "Дата создания пользователя", example = "2007-12-03T10:15:30")
    private final LocalDateTime createdAt;
    @Schema(description = "Дата последнего изменения пользователя", example = "2007-12-03T10:15:30.")
    private final LocalDateTime updatedAt;
}
