package ru.itis.semester_work.impl.controller.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.semester_work.api.dto.response.AppErrorResponse;
import ru.itis.semester_work.impl.controller.ImageController;
import ru.itis.semester_work.impl.controller.OrganizationRestController;
import ru.itis.semester_work.impl.exception.DataBaseException;
import ru.itis.semester_work.impl.exception.conflict.ConflictException;
import ru.itis.semester_work.impl.exception.not_found.NotFoundException;
import ru.itis.semester_work.impl.util.ValidationUtil;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {OrganizationRestController.class, ImageController.class})
@Slf4j
public class AppRestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppErrorResponse handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        log.error("Ошибка ресурс не найден: {}", e.getMessage());
        return new AppErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public AppErrorResponse handleEmailAlreadyExistsException(ConflictException e, HttpServletRequest request) {
        return new AppErrorResponse(HttpStatus.CONFLICT, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("Ошибка валидации: {}", e.getMessage());
        Map<String, String> errors = ValidationUtil.getValidationErrors(e.getBindingResult());
        return new AppErrorResponse(HttpStatus.BAD_REQUEST, "Ошибка валидации", request.getRequestURI(), errors);
    }

    @ExceptionHandler(DataBaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppErrorResponse handleDataBaseException(DataBaseException e, HttpServletRequest request) {
        return new AppErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request.getRequestURI());
    }

}
