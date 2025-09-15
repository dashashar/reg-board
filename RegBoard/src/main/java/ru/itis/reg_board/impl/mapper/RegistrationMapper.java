package ru.itis.reg_board.impl.mapper;

import ru.itis.reg_board.api.dto.request.FieldAnswerRequest;
import ru.itis.reg_board.api.dto.response.FieldResponse;
import ru.itis.reg_board.api.dto.response.RegistrationTableResponse;
import ru.itis.reg_board.impl.model.*;

import java.util.List;

public interface RegistrationMapper {

    RegistrationEntity toEntity(long accountId, long eventId);

    FieldAnswerEntity toEntity(FieldAnswerRequest request, RegistrationEntity registration);

    RegistrationTableResponse toResponse(String eventTitle, List<FieldResponse> fields, List<RegistrationEntity> registrations);

}
