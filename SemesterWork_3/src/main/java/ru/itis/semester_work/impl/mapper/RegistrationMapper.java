package ru.itis.semester_work.impl.mapper;

import ru.itis.semester_work.api.dto.request.FieldAnswerRequest;
import ru.itis.semester_work.api.dto.response.FieldResponse;
import ru.itis.semester_work.api.dto.response.RegistrationTableResponse;
import ru.itis.semester_work.impl.model.*;

import java.util.List;

public interface RegistrationMapper {

    RegistrationEntity toEntity(long accountId, long eventId);

    FieldAnswerEntity toEntity(FieldAnswerRequest request, RegistrationEntity registration);

    RegistrationTableResponse toResponse(String eventTitle, List<FieldResponse> fields, List<RegistrationEntity> registrations);

}
