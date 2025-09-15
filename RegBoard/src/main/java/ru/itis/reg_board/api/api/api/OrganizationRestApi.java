package ru.itis.reg_board.api.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.reg_board.api.dto.request.AddAdminRequest;
import ru.itis.reg_board.api.dto.request.OrganizationRequest;
import ru.itis.reg_board.api.dto.response.*;
import ru.itis.reg_board.impl.security.details.AccountUserDetails;

import java.util.List;

@Tag(name = "API организаций", description = "API для добавления, получения, обновления, удаления организаций и добавления, удаления администраторов")
@RequestMapping("/api/org")
public interface OrganizationRestApi {

    @Operation(
            summary = "Получение всех организаций",
            description = "Извлекает краткое описание всех организаций аккаунта",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное выполнение",
                            content = @Content(array = @ArraySchema(
                                    schema = @Schema(implementation = OrganizationBriefResponse.class)))),
                    @ApiResponse(responseCode = "500", description = "Ошибка базы данных",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = {@ExampleObject(value = """
                                            {
                                                "timestamp": "2025-05-28T14:01:26.2366544",
                                                "status": 500,
                                                "error": "Не удалось получить данные организаций",
                                                "path": "/api/org/account"
                                            }""")}))})
    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    List<OrganizationBriefResponse> getAllBriefOrganizations(@AuthenticationPrincipal AccountUserDetails userDetails);

    @Operation(
            summary = "Получение организации",
            description = "Извлекает данные организации по id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное выполнение",
                            content = @Content(schema = @Schema(implementation = OrganizationAdminResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Организация не найдена",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                 "timestamp": "2025-06-13T16:36:47.812759",
                                                  "status": 404,
                                                  "error": "Организация не найдена",
                                                  "path": "/api/org/1000000"
                                            }"""))),
                    @ApiResponse(responseCode = "500", description = "Ошибка базы данных",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "timestamp": "2025-05-28T14:01:26.2366544",
                                                "status": 500,
                                                "error": "Не удалось получить организацию",
                                                "path": "/api/org/1000000"
                                            }""")))})
    @GetMapping("/{orgId}")
    @ResponseStatus(HttpStatus.OK)
    OrganizationAdminResponse getOrganization(@PathVariable("orgId") long orgId);

    @Operation(
            summary = "Создание организации",
            description = "Отправляет данные на создание организации",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешное создание",
                            content = @Content(schema = @Schema(implementation = OrganizationAdminResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Неккоректные введеные данные",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Аккаунт не найден",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                 "timestamp": "2025-06-13T16:36:47.812759",
                                                  "status": 404,
                                                  "error": "Аккаунт не найден",
                                                  "path": "/api/org/account"
                                            }"""))),
                    @ApiResponse(responseCode = "500", description = "Ошибка базы данных",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "timestamp": "2025-05-28T14:01:26.2366544",
                                                "status": 500,
                                                "error": "Не удалось создать организацию",
                                                "path": "/api/org/account"
                                            }""")))})
    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    OrganizationAdminResponse createOrganization(@AuthenticationPrincipal AccountUserDetails userDetails,
                                                 @Valid @RequestBody OrganizationRequest organizationRequest);

    @Operation(
            summary = "Обновление организации",
            description = "Отправляет данные для обновление информации по организации",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное выполнение",
                            content = @Content(schema = @Schema(implementation = OrganizationResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Неккоректные введеные данные",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Организация не найдена",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                 "timestamp": "2025-06-13T16:36:47.812759",
                                                  "status": 404,
                                                  "error": "Организация не найдена",
                                                  "path": "/api/org/1000000"
                                            }
                                            """))),
                    @ApiResponse(responseCode = "500", description = "Ошибка базы данных",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "timestamp": "2025-05-28T14:01:26.2366544",
                                                "status": 500,
                                                "error": "Не удалось отредактировать данные организации",
                                                "path": "/api/org/1000000"
                                            }
                                            """)))})
    @PutMapping("/{orgId}")
    @ResponseStatus(HttpStatus.OK)
    OrganizationResponse updateOrganization(@PathVariable("orgId") long orgId,
                                            @Valid @RequestBody OrganizationRequest organizationRequest);

    @Operation(
            summary = "Удаление организации",
            description = "Отправляет запрос на удаление организации по id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Успешное удаление",
                            content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Организация не найдена",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                 "timestamp": "2025-06-13T16:36:47.812759",
                                                  "status": 404,
                                                  "error": "Организация не найдена",
                                                  "path": "/api/org/1000000"
                                            }"""))),
                    @ApiResponse(responseCode = "500", description = "Ошибка базы данных",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "timestamp": "2025-05-28T14:01:26.2366544",
                                                "status": 500,
                                                "error": "Не удалось удалить организацию",
                                                "path": "/api/org/1000000"
                                            }""")))})
    @DeleteMapping("/{orgId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrganization(@PathVariable("orgId") long orgId);

    @Operation(
            summary = "Добавление администратора",
            description = "Отправляет данные на добавление администратора",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешное создание",
                            content = @Content(schema = @Schema(implementation = AccountResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Неккоректные введеные данные",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "timestamp": "2025-06-13T19:09:03.0366087",
                                                "status": 400,
                                                "error": "Ошибка валидации",
                                                "path": "/api/org/10000000",
                                                "details": {
                                                    "email": "Некорректный email"
                                                }
                                            }"""))),
                    @ApiResponse(responseCode = "404", description = "Не найден аккаунт или организация",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                 "timestamp": "2025-06-13T16:36:47.812759",
                                                 "status": 404,
                                                 "error": "Аккаунт не найден",
                                                 "path": "/api/org/10000000"
                                            }"""))),
                    @ApiResponse(responseCode = "409", description = "Конфликт с существующими данными",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                 "timestamp": "2025-06-13T16:36:47.812759",
                                                 "status": 404,
                                                 "error": "Аккаунт уже добавлен как админ организации",
                                                 "path": "/api/org/10000000"
                                            }"""))),
                    @ApiResponse(responseCode = "500", description = "Ошибка базы данных",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "timestamp": "2025-05-28T14:01:26.2366544",
                                                "status": 500,
                                                "error": "Не удалось добавить админа",
                                                "path": "/api/org/10000000"
                                            }
                                            """)))})
    @PostMapping("/{orgId}")
    @ResponseStatus(HttpStatus.CREATED)
    AccountResponse addAdmin(@PathVariable("orgId") long orgId,
                             @Valid @RequestBody AddAdminRequest request);

    @Operation(
            summary = "Удаление администратора",
            description = "Отправляет запрос на удаление администратора по id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Успешное удаление",
                            content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Не найден аккаунт или организация",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                 "timestamp": "2025-06-13T16:36:47.812759",
                                                  "status": 404,
                                                  "error": "Организация не найдена",
                                                  "path": "/api/org/1000000/account/1000000"
                                            }"""))),
                    @ApiResponse(responseCode = "500", description = "Ошибка базы данных",
                            content = @Content(schema = @Schema(implementation = AppErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "timestamp": "2025-05-28T14:01:26.2366544",
                                                "status": 500,
                                                "error": "Не удалось удалить администратора",
                                                "path": "/api/org/1000000/account/1000000"
                                            }""")))})
    @DeleteMapping("/{orgId}/account/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAdmin(@PathVariable("orgId") long orgId,
                     @PathVariable("accountId") long accountId);
}
