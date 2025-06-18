package ru.itis.semester_work.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание организации")
public record OrganizationRequest (
        @Schema(description = "Название организации", example = "Эпишура")
        @NotBlank(message = "Название не может быть пустым")
        @Size(max = 255, message = "Название не должно превышать 255 символов")
        String name,

        @Schema(description = "Описание организации", example = "Волонтерская организация")
        @NotBlank(message = "Описание не может быть пустым")
        @Size(max = 500, message = "Описание не должно превышать 500 символов")
        String description,

        @Schema(description = "Почтовый адрес организации", example = "epishura@gmail.com")
        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Некорректный email")
        @Size(max = 255, message = "Название не должно превышать 255 символов")
        String email

) {}
