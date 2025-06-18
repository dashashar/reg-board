package ru.itis.semester_work.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Информация по организации со списком администраторов")
public record OrganizationAdminResponse(
        @Schema(description = "id организации", example = "10000000")
        Long id,
        @Schema(description = "Название организации", example = "Эпишура")
        String name,
        @Schema(description = "Описание организации", example = "Волонтерская организация")
        String description,
        @Schema(description = "Почтовый адрес организации", example = "epishura@gmail.com")
        String email,
        @Schema(description = "Администраторы организации", example = """
        [
            {
                "id": 10000000,
                "firstName": "dasha",
                "lastName": "sha",
                "email": "dasha@mail.ru"
            }
        ]""")
        List<AccountResponse> admins
) {}
