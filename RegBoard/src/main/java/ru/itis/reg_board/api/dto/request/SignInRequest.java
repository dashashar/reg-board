package ru.itis.reg_board.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(
        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Некорректный email")
        @Size(max = 255, message = "Email не должен превышать 255 символов")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(max = 30, message = "Пароль не должен превышать 30 символов")
        String password
) {}
