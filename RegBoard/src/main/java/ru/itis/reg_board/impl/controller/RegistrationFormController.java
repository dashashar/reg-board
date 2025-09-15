package ru.itis.reg_board.impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.itis.reg_board.api.api.api.RegFormApi;
import ru.itis.reg_board.api.dto.request.FieldRequest;
import ru.itis.reg_board.api.dto.response.FieldResponse;
import ru.itis.reg_board.impl.service.FormFieldService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegistrationFormController implements RegFormApi {

    private final FormFieldService formService;

    @Override
    public String getFormBuilderPage(long orgId, long eventId, Model model) {
        List<FieldResponse> fields = formService.getFormFields(eventId);
        model.addAttribute("orgId", String.valueOf(orgId));
        model.addAttribute("eventId", String.valueOf(eventId));
        model.addAttribute("fields", fields);
        return "reg_form/form_builder";
    }

    @Override
    public String saveChangesForm(long orgId, long eventId, List<FieldRequest> formFields) {
        formService.updateFormFields(orgId, eventId, formFields);
        return "redirect:/org/%s/event".formatted(orgId);
    }

}
