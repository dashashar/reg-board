package ru.itis.semester_work.impl.service;

import org.springframework.data.domain.Page;
import ru.itis.semester_work.api.dto.request.FieldAnswerRequest;
import ru.itis.semester_work.api.dto.response.EventBriefResponse;
import ru.itis.semester_work.api.dto.response.EventDetailsResponse;
import ru.itis.semester_work.api.dto.response.RegistrationTableResponse;
import ru.itis.semester_work.api.dto.response.ConfirmationRegistrationResponse;

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
