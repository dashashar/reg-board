package ru.itis.reg_board.api.dto.response;

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
