package ru.itis.reg_board.impl.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.reg_board.api.dto.request.EventRequest;
import ru.itis.reg_board.api.dto.response.CategoryEventResponse;
import ru.itis.reg_board.api.dto.response.EventBriefResponse;
import ru.itis.reg_board.api.dto.response.EventDetailsResponse;
import ru.itis.reg_board.api.dto.response.EventResponse;
import java.util.List;

public interface EventService {

    List<CategoryEventResponse> getAllCategoriesEvent();

    List<EventBriefResponse> getAllOrgEventsBrief(long orgId);

    Page<EventBriefResponse> getAllEvents(Pageable pageable);

    Page<EventBriefResponse> getAllEventsByCategory(short categoryId, Pageable pageable);

    long createEvent(long orgId, EventRequest request);

    EventResponse getEvent(long eventId);

    EventDetailsResponse getEventWithDetails(long eventId);

    void deleteEvent(long orgId, long eventId);

    void updateEvent(long orgId, long eventId, EventRequest request);

    void checkOrgMismatch(long orgId, long eventId);

    String getEventTitleById(long eventId);

}
