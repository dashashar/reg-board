package ru.itis.reg_board.impl.exception.not_found;

public class OrganizationNotFoundException extends NotFoundException {
    public OrganizationNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
