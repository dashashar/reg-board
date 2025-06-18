package ru.itis.semester_work.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на загрузку картинки")
public record ImageUploadResponse(
        @Schema(description = "id загруженной картинки", example = "356dce9b-fd4a-4d17-b912-f3e14f8cdff5")
        String imgId
) {}
