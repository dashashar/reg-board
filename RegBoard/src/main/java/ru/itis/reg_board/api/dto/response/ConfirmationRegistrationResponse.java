package ru.itis.reg_board.api.dto.response;

import java.util.UUID;

public record ConfirmationRegistrationResponse(
        int httpStatusCode,
        String registrationStatus,
        String message,
        UUID registrationId
) {}
