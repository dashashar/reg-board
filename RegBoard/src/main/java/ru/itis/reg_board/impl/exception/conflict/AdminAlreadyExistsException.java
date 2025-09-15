package ru.itis.reg_board.impl.exception.conflict;

public class AdminAlreadyExistsException extends ConflictException{
    public AdminAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
