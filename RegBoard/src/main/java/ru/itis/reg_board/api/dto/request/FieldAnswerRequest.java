package ru.itis.reg_board.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record FieldAnswerRequest(
        @NotNull
        Long fieldId,

        String answerValue
) {}
