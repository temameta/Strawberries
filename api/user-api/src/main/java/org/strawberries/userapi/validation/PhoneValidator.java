package org.strawberries.userapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private static final Pattern CORRECT_PHONE = Pattern.compile("^\\+7\\d{10}$");
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()) return true;
        if (s.length() != 12) return false;
        return CORRECT_PHONE.matcher(s).matches();
    }
}
