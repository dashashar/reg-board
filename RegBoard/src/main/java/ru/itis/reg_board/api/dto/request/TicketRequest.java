package ru.itis.reg_board.api.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record TicketRequest(

        @NotBlank(message = "Название не может быть пустым")
        @Size(max = 100, message = "Название не должно превышать 100 символов")
        String name,

        @NotNull(message = "Цена не может быть пустой")
        @Min(value = 0, message = "Значение должно быть равно или больше 0")
        Integer price,

        @Min(value = 0, message = "Значение должно быть равно или больше 0")
        @NotNull(message = "Количество билетов не может быть пустой")
        Integer totalTickets,

        @NotNull(message = "Время начала продаж не может быть пустым")
        LocalDateTime salesStart,

        @NotNull(message = "Время окончания продаж не может быть пустым")
        LocalDateTime salesEnd
) {}
