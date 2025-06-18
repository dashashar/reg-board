package ru.itis.semester_work.impl.exception.not_found;

import ru.itis.semester_work.impl.exception.AppException;

public class NotFoundException extends AppException {

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
