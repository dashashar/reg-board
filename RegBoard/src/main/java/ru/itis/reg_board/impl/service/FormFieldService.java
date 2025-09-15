package ru.itis.reg_board.impl.service;

import ru.itis.reg_board.api.dto.request.FieldRequest;
import ru.itis.reg_board.api.dto.response.FieldResponse;

import java.util.List;

public interface FormFieldService {

    void updateFormFields(long orgId, long eventId, List<FieldRequest> fields);

    List<FieldResponse> getFormFields(long eventId);

}
