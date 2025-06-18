package ru.itis.semester_work.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FieldRequest(
        Long id,

        @NotBlank(message = "Тип поля не может быть пустым")
        String fieldType,

        @NotBlank(message = "Вопрос не может быть пустым")
        @Size(max = 500, message = "Вопрос не должен превышать 500 символов")
        String question,

        @NotNull(message = "Должна быть указана обязательность поля")
        Boolean isRequired,

        @NotNull(message = "Позиция поля не может быть пустой")
        @Positive(message = "Позиция поля должна быть положительным числом")
        Short position,

        @Size(max = 4000, message = "Варианты ответа не должны превышать 4000 символов")
        String options,

        Boolean deleted
) {}
