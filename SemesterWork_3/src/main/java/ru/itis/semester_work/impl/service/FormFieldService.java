package ru.itis.semester_work.impl.service;

import ru.itis.semester_work.api.dto.request.FieldRequest;
import ru.itis.semester_work.api.dto.response.FieldResponse;

import java.util.List;

public interface FormFieldService {

    void updateFormFields(long orgId, long eventId, List<FieldRequest> fields);

    List<FieldResponse> getFormFields(long eventId);

}
