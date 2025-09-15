package ru.itis.reg_board.impl.exception;

public class DataBaseException extends AppException{

    public DataBaseException(String errorMessage) {
        super(errorMessage);
    }

    public DataBaseException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

}