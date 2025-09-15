package ru.itis.reg_board.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными огранизации")
public record OrganizationResponse (
        @Schema(description = "id организации", example = "10000000")
        Long id,
        @Schema(description = "Название организации", example = "Эпишура")
        String name,
        @Schema(description = "Описание организации", example = "Волонтерская организация")
        String description,
        @Schema(description = "Почтовый адрес организации", example = "epishura@gmail.com")
        String email
) {}
