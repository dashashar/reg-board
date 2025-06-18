package ru.itis.semester_work.impl.exception.bad_request;

public class AvailableNumberTicketsException extends BadRequestException{
    public AvailableNumberTicketsException(String errorMessage) {
        super(errorMessage);
    }
}
