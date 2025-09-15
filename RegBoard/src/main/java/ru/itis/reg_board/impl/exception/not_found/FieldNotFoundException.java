package ru.itis.reg_board.impl.exception.not_found;

public class FieldNotFoundException extends NotFoundException {
    public FieldNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
