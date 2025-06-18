package ru.itis.semester_work.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.semester_work.api.dto.response.AppErrorResponse;
import ru.itis.semester_work.api.dto.response.ImageUploadResponse;
import ru.itis.semester_work.api.dto.response.OrganizationBriefResponse;

@Tag(name = "API картинок", description = "API для загрузки, получения и удаления картинок")
@RequestMapping("/api/image")
public interface ImageApi {

    @Operation(
            summary = "Получение картинки",
            description = "Извлекает картинку",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное выполнение",
                            content = @Content(array = @ArraySchema(
                                    schema = @Schema(implementation = ResponseEntity.class)))),
                    @ApiResponse(responseCode = "500", description = "Ошибка базы данных",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = {@ExampleObject(value = """
                                            {
                                                "timestamp": "2025-05-28T14:01:26.2366544",
                                                "status": 500,
                                                "error": "Не удалось получить файл",
                                                "path": "/api/image/356dce9b-fd4a-4d17-b912-f3e14f8cdff5"
                                            }""")}))})
    @GetMapping("/{fileId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<byte[]> getImage(@PathVariable String fileId);

    @Operation(
            summary = "Загрузка картинки",
            description = "Загружает картинку",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешная загрузка",
                            content = @Content(array = @ArraySchema(
                                    schema = @Schema(implementation = ResponseEntity.class)))),
                    @ApiResponse(responseCode = "500", description = "Ошибка базы данных",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = {@ExampleObject(value = """
                                            {
                                                "timestamp": "2025-05-28T14:01:26.2366544",
                                                "status": 500,
                                                "error": "Не удалось загрузить файл",
                                                "path": "/api/image/upload"
                                            }""")}))})
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    ImageUploadResponse uploadImage(@RequestParam("file") MultipartFile file);

    @Operation(
            summary = "Удаление картинки",
            description = "Отправляет запрос на удаление картинки по id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Успешное удаление",
                            content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка базы данных",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "timestamp": "2025-05-28T14:01:26.2366544",
                                                "status": 500,
                                                "error": "Не удалось удалить файл",
                                                "path": "/api/image/356dce9b-fd4a-4d17-b912-f3e14f8cdff5"
                                            }""")))})
    @DeleteMapping("/{fileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteImage(@PathVariable String fileId);

}
