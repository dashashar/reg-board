package ru.itis.semester_work.impl.util;

import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ValidationUtil {

    public Map<String, String> getValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        return errors;
    }

}
