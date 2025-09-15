package ru.itis.reg_board.impl.exception.bad_request;

public class AvailableNumberTicketsException extends BadRequestException{
    public AvailableNumberTicketsException(String errorMessage) {
        super(errorMessage);
    }
}
