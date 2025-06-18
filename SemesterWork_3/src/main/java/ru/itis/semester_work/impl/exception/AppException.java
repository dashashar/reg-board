package ru.itis.semester_work.impl.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    public AppException(String errorMessage) {
        super(errorMessage);
    }

    public AppException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

}