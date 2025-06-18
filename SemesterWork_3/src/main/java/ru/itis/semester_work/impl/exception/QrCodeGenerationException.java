package ru.itis.semester_work.impl.exception;

public class QrCodeGenerationException extends AppException{
    public QrCodeGenerationException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public QrCodeGenerationException(String errorMessage) {
        super(errorMessage);
    }
}
