package ru.itis.reg_board.api.dto.request;

import jakarta.validation.constraints.*;

public record SignUpRequest(

        @NotBlank(message = "Имя не может быть пустым")
        @Size(min = 2, max = 30, message = "Имя должно иметь длину от 2 до 30 символов")
        String firstName,

        @NotBlank(message = "Фамилия не может быть пустой")
        @Size(min = 2, max = 30, message = "Фамилия должна иметь длину от 2 до 30 символов")
        String lastName,

        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Некорректный email")
        @Size(max = 255, message = "Email не должен превышать 255 символов")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{5,30}$",
                message = "Пароль должен быть от 5 до 30 символов, иметь цифры и строчные, заглавные латинские буквы")
        String password
) {}
