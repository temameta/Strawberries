package org.strawberries.userapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Phone {
    String message() default "Некорректный номер телефона. Допустимый формат: +79998886655";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
