package ru.itis.reg_board.impl.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.reg_board.impl.exception.DataBaseException;
import ru.itis.reg_board.impl.exception.bad_request.BadRequestException;
import ru.itis.reg_board.impl.exception.conflict.ConflictException;
import ru.itis.reg_board.impl.exception.InvalidPasswordException;
import ru.itis.reg_board.impl.exception.not_found.NotFoundException;
import ru.itis.reg_board.impl.util.ValidationUtil;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e, Model model) {
        log.error("Ошибка ресурс не найден: {}", e.getMessage());
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(BadRequestException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEmailAlreadyExistsException(ConflictException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleInvalidPasswordException(InvalidPasswordException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Model model) {
        log.error("Ошибка валидации: {}", e.getMessage());
        Map<String, String> errors = ValidationUtil.getValidationErrors(e.getBindingResult());
        model.addAttribute("errors", errors);
        return "error";
    }

    @ExceptionHandler(DataBaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDataBaseException(DataBaseException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

}
