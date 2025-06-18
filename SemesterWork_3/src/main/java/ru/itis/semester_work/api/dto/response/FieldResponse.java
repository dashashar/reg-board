package ru.itis.semester_work.api.dto.response;

public record FieldResponse(
        String id,
        String fieldType,
        String question,
        Boolean isRequired,
        Short position,
        String options
) {}
