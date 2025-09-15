package ru.itis.reg_board.impl.exception.bad_request;

public class IncorrectDataException extends BadRequestException{
    public IncorrectDataException(String errorMessage) {
        super(errorMessage);
    }
}
