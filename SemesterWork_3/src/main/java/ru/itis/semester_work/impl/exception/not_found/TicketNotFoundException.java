package ru.itis.semester_work.impl.exception.not_found;

public class TicketNotFoundException extends NotFoundException {
    public TicketNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
