package ru.itis.reg_board.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Стандартный ответ ошибки")
public record AppErrorResponse(
        @Schema(description = "Дата и время, когда произошла ошибка", example = "2025-05-28T14:45:00.0051508")
        LocalDateTime timestamp,

        @Schema(description = "Статусный код HTTP", example = "400")
        int status,

        @Schema(description = "Описание ошибки", example = "Ошибка валидации")
        String error,

        @Schema(description = "Путь API, где произошла ошибка", example = "/api/org/account")
        String path,

        @Schema(description = "Дополнительные сведения при ошибках валидации",
                example = """
                {
                      "name": "Название не может быть пустым",
                      "description": "Описание не может быть пустым",
                      "email": "Некорректный email"
                }
                """,
                nullable = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Map<String, String> details
) {
    public AppErrorResponse(HttpStatus status, String error, String path) {
        this(LocalDateTime.now(), status.value(), error, path,null);
    }

    public AppErrorResponse(HttpStatus status, String error, String path, Map<String, String> details) {
        this(LocalDateTime.now(), status.value(), error, path, details);
    }
}

