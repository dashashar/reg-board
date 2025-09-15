package ru.itis.reg_board.impl.exception.bad_request;

import ru.itis.reg_board.impl.exception.AppException;

public class BadRequestException extends AppException {
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
