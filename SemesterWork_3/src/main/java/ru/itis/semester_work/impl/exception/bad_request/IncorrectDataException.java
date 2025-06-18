package ru.itis.semester_work.impl.exception.bad_request;

public class IncorrectDataException extends BadRequestException{
    public IncorrectDataException(String errorMessage) {
        super(errorMessage);
    }
}
