package ru.itis.reg_board.impl.exception.conflict;

public class EmailAlreadyExistsException extends ConflictException {

    public EmailAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
