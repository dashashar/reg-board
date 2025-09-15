package ru.itis.reg_board.impl.exception;

public class InvalidPasswordException extends AppException {

    public InvalidPasswordException(String errorMessage) {
        super(errorMessage);
    }

}
