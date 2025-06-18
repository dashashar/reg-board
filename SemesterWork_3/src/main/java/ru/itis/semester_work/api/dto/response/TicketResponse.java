package ru.itis.semester_work.api.dto.response;

import java.time.LocalDateTime;

public record TicketResponse(
        String name,
        Integer price,
        Integer totalTickets,
        Integer soldTickets,
        LocalDateTime salesStart,
        LocalDateTime salesEnd
) {}
