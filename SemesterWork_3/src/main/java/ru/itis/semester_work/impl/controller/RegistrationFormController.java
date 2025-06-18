package ru.itis.semester_work.impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.itis.semester_work.api.api.RegFormApi;
import ru.itis.semester_work.api.dto.request.FieldRequest;
import ru.itis.semester_work.api.dto.response.FieldResponse;
import ru.itis.semester_work.impl.service.FormFieldService;

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
