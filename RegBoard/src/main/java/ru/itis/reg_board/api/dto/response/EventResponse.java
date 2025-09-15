package ru.itis.reg_board.api.dto.response;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String description,
        LocalDateTime timeStart,
        LocalDateTime timeEnd,
        String city,
        String address,
        Short categoryId,
        String imgId
) {}
