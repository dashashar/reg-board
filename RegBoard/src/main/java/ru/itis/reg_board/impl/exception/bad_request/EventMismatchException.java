package ru.itis.reg_board.impl.exception.bad_request;

public class EventMismatchException extends BadRequestException{
    public EventMismatchException(String errorMessage) {
        super(errorMessage);
    }
}
