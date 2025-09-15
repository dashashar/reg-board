package ru.itis.reg_board.impl.exception.bad_request;

public class OrganizationMismatchException extends BadRequestException {
    public OrganizationMismatchException(String errorMessage) {
        super(errorMessage);
    }
}
