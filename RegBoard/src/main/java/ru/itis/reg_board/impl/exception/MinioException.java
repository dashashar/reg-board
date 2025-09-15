package ru.itis.reg_board.impl.exception;

public class MinioException extends DataBaseException{
    public MinioException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
