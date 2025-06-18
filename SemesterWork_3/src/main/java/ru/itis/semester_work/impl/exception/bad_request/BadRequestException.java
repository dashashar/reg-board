package ru.itis.semester_work.impl.exception.bad_request;

import ru.itis.semester_work.impl.exception.AppException;

public class BadRequestException extends AppException {
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
