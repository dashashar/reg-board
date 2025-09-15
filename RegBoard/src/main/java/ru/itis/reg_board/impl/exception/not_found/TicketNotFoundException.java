package ru.itis.reg_board.impl.exception.not_found;

public class TicketNotFoundException extends NotFoundException {
    public TicketNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
