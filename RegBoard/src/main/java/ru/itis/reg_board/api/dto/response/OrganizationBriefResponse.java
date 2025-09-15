package ru.itis.reg_board.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Краткая информация по организации")
public record OrganizationBriefResponse(
        @Schema(description = "id организации", example = "10000000")
        Long id,
        @Schema(description = "Название организации", example = "Эпишура")
        String name
) {}
