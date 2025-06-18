package ru.itis.semester_work.impl.exception.bad_request;

public class EventMismatchException extends BadRequestException{
    public EventMismatchException(String errorMessage) {
        super(errorMessage);
    }
}
