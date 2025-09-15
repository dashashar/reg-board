package ru.itis.reg_board.impl.exception.conflict;

public class RegistrationAlreadyExistsException extends ConflictException{
    public RegistrationAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
