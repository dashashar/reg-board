package ru.itis.reg_board.impl.exception.conflict;

import lombok.Getter;

@Getter
public class TicketAlreadyExistsException extends ConflictException {

    private final Long ticketId;

    public TicketAlreadyExistsException(String errorMessage, Long ticketId) {
        super(errorMessage);
        this.ticketId = ticketId;
    }
}
