package ru.itis.semester_work.impl.exception.conflict;

public class EmailAlreadyExistsException extends ConflictException {

    public EmailAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
