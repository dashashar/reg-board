package ru.itis.semester_work.impl.exception.conflict;

public class RegistrationAlreadyExistsException extends ConflictException{
    public RegistrationAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
