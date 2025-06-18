package ru.itis.semester_work.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными аккаунта")
public record AccountResponse(
        @Schema(description = "id аккаунта", example = "10000005")
        long id,
        @Schema(description = "Имя", example = "Мария")
        String firstName,
        @Schema(description = "Фамилия", example = "Попова")
        String lastName,
        @Schema(description = "Почтовый адрес", example = "masha@mail.ru")
        String email
) {}
