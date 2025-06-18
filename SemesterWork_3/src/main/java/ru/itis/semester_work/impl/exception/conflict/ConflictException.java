package ru.itis.semester_work.impl.exception.conflict;

import ru.itis.semester_work.impl.exception.AppException;

public class ConflictException extends AppException {
    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}
