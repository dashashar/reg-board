package ru.itis.semester_work.impl.exception;

public class DataBaseException extends AppException{

    public DataBaseException(String errorMessage) {
        super(errorMessage);
    }

    public DataBaseException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

}