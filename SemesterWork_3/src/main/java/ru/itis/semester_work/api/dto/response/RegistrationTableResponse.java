package ru.itis.semester_work.api.dto.response;

import java.util.List;

public record RegistrationTableResponse(
        String eventTitle,
        List<String> headers,
        List<List<String>> rows
) {}
