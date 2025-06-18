package ru.itis.semester_work.impl.exception.bad_request;

public class OrganizationMismatchException extends BadRequestException {
    public OrganizationMismatchException(String errorMessage) {
        super(errorMessage);
    }
}
