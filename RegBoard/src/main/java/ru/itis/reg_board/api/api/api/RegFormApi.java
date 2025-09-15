package ru.itis.reg_board.api.api.api;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.reg_board.api.dto.request.FieldRequest;

import java.util.List;

@RequestMapping("/org/{orgId}/event/{eventId}/form")
public interface RegFormApi {

    @GetMapping
    String getFormBuilderPage(@PathVariable long orgId,
                              @PathVariable long eventId,
                              Model model);

    @PostMapping
    String saveChangesForm (
            @PathVariable long orgId,
            @PathVariable long eventId,
            @Valid @RequestBody List<FieldRequest> formFields);

}
