package ru.itis.semester_work.impl.exception.conflict;

public class AdminAlreadyExistsException extends ConflictException{
    public AdminAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
