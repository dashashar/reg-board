package ru.itis.semester_work.impl.exception.not_found;

public class OrganizationNotFoundException extends NotFoundException {
    public OrganizationNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
