package ru.itis.reg_board.impl.exception.not_found;

public class EventNotFoundException extends NotFoundException {

    public EventNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
