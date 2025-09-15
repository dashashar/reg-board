package ru.itis.reg_board.impl.exception.conflict;

import ru.itis.reg_board.impl.exception.AppException;

public class ConflictException extends AppException {
    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}
