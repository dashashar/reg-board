package ru.itis.semester_work.api.dto.response;

import java.util.UUID;

public record ConfirmationRegistrationResponse(
        int httpStatusCode,
        String registrationStatus,
        String message,
        UUID registrationId
) {}
