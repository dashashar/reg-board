package ru.itis.semester_work.impl.exception;

public class MinioException extends DataBaseException{
    public MinioException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
