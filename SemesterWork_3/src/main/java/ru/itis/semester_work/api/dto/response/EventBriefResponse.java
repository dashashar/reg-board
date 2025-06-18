package ru.itis.semester_work.api.dto.response;

import java.time.LocalDateTime;

public record EventBriefResponse(
        String id,
        String title,
        String timeStart,
        String timeEnd,
        String city,
        String address,
        String ticketId,
        Integer price,
        String imgId) {
}
