package ru.itis.reg_board.api.dto.response;

import java.util.List;

public record RegistrationTableResponse(
        String eventTitle,
        List<String> headers,
        List<List<String>> rows
) {}
