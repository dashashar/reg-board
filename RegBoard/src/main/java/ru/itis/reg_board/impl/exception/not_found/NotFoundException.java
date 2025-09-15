package ru.itis.reg_board.impl.exception.not_found;

import ru.itis.reg_board.impl.exception.AppException;

public class NotFoundException extends AppException {

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
