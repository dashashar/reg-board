package ru.itis.reg_board.api.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EventRequest(

        @NotBlank(message = "Название не может быть пустым")
        @Size(max = 255, message = "Название не должно превышать 255 символов")
        String title,

        @NotBlank(message = "Описание не может быть пустым")
        @Size(max = 4000, message = "Описание не должно превышать 4000 символов")
        String description,

        @NotNull(message = "Дата начала не может быть пустой")
        @Future(message = "Дата начала должна быть в будущем")
        LocalDateTime timeStart,

        @NotNull(message = "Дата окончания не может быть пустой")
        @Future(message = "Дата окончания должна быть в будущем")
        LocalDateTime timeEnd,

        @NotBlank(message = "Город не может быть пустым")
        @Size(max = 255, message = "Название города не должно превышать 255 символов")
        String city,

        @NotBlank(message = "Адрес не может быть пустым")
        @Size(max = 255, message = "Адрес не должно превышать 255 символов")
        String address,

        @NotNull(message = "Категория не может быть пустой")
        Short categoryId,

        String imgId
) {}
