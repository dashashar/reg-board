package ru.itis.semester_work.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record FieldAnswerRequest(
        @NotNull
        Long fieldId,

        String answerValue
) {}
