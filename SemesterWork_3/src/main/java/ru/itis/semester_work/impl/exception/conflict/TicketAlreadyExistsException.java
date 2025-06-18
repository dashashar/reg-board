package ru.itis.semester_work.impl.exception.conflict;

import lombok.Getter;

@Getter
public class TicketAlreadyExistsException extends ConflictException {

    private final Long ticketId;

    public TicketAlreadyExistsException(String errorMessage, Long ticketId) {
        super(errorMessage);
        this.ticketId = ticketId;
    }
}
