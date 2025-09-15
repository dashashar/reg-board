package ru.itis.reg_board.impl.service;

import org.springframework.data.domain.Page;
import ru.itis.reg_board.api.dto.request.FieldAnswerRequest;
import ru.itis.reg_board.api.dto.response.EventBriefResponse;
import ru.itis.reg_board.api.dto.response.EventDetailsResponse;
import ru.itis.reg_board.api.dto.response.RegistrationTableResponse;
import ru.itis.reg_board.api.dto.response.ConfirmationRegistrationResponse;

import java.util.List;
import java.util.UUID;

public interface RegistrationService {

    Page<EventBriefResponse> getAllEvents(Short categoryId, int page, int size);

    void createRegistration(long accountId, long eventId, List<FieldAnswerRequest> answer);

    EventDetailsResponse getEventDetails(long accountId, long eventId);

    List<EventBriefResponse> getAllEventsAccountHasRegistration(long accountId);

    RegistrationTableResponse getAllRegistrationsByEvent(long orgId, long eventId);

    ConfirmationRegistrationResponse confirmRegistration(UUID registrationId);

}
