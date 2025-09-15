package ru.itis.reg_board.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на добавление администратора")
public record AddAdminRequest (
        @Schema(description = "Email аккаунта, которого надо сделать администратором", example = "masha@mail.ru")
        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Некорректный email")
        @Size(max = 255, message = "Email не должен превышать 255 символов")
        String email
) {}
