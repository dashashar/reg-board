package ru.itis.reg_board.api.dto.response;

public record FieldResponse(
        String id,
        String fieldType,
        String question,
        Boolean isRequired,
        Short position,
        String options
) {}
