package ru.itis.semester_work.impl.service.impl;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.semester_work.api.dto.request.EventRequest;
import ru.itis.semester_work.api.dto.response.*;
import ru.itis.semester_work.impl.exception.DataBaseException;
import ru.itis.semester_work.impl.exception.bad_request.OrganizationMismatchException;
import ru.itis.semester_work.impl.exception.not_found.EventNotFoundException;
import ru.itis.semester_work.impl.mapper.CategoryEventMapper;
import ru.itis.semester_work.impl.mapper.EventMapper;
import ru.itis.semester_work.impl.model.CategoryEventEntity;
import ru.itis.semester_work.impl.model.EventEntity;
import ru.itis.semester_work.impl.model.OrganizationEntity;
import ru.itis.semester_work.impl.repository.CategoryEventRepository;
import ru.itis.semester_work.impl.repository.EventRepository;
import ru.itis.semester_work.impl.service.EventService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final CategoryEventRepository categoryRepository;
    private final CategoryEventMapper categoryMapper;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<CategoryEventResponse> getAllCategoriesEvent() {
        List<CategoryEventEntity> categories = categoryRepository.getAll();
        return categoryMapper.toResponseList(categories);
    }

    @Override
    public List<EventBriefResponse> getAllOrgEventsBrief(long orgId) {
        List<Tuple> tuples = eventRepository.findBriefEventsByOrgId(orgId);
        return tuples.stream()
                .map(eventMapper::toEventBriefResponse)
                .toList();
    }

    @Override
    public Page<EventBriefResponse> getAllEvents(Pageable pageable) {
        return eventRepository.findAllBriefEvents(pageable)
                .map(eventMapper::toEventBriefResponse);
    }

    @Override
    public Page<EventBriefResponse> getAllEventsByCategory(short categoryId, Pageable pageable) {
        return eventRepository.findAllBriefEventsByCategory(categoryId, pageable)
                .map(eventMapper::toEventBriefResponse);
    }

    @Override
    public long createEvent(long orgId, EventRequest request) {
        try {
            EventEntity event = eventMapper.toEntity(request);
            event.setOrganization(OrganizationEntity.builder().id(orgId).build());
            return eventRepository.save(event).getId();
        } catch (DataAccessException e) {
            log.error("Не удалось создать событие для организации {}: {}", orgId, e.getMessage());
            throw new DataBaseException("Не удалось создать событие", e);
        }
    }

    @Override
    public EventResponse getEvent(long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Событие не найдено"));
        return eventMapper.toResponse(event);
    }

    @Override
    public EventDetailsResponse getEventWithDetails(long eventId) {
        EventEntity event = eventRepository.findEventWithTicketAndOrganization(eventId)
                .orElseThrow(() -> new EventNotFoundException("Событие не найдено"));
        return eventMapper.toDetailsResponse(event);
    }

    @Override
    @Transactional
    public void deleteEvent(long orgId, long eventId) {
        try {
            checkOrgMismatch(orgId, eventId);
            eventRepository.deleteById(eventId);
        } catch(DataAccessException e) {
            log.error("Не удалось удалить событие {}: {}", eventId, e.getMessage());
            throw new DataBaseException("Не удалось удалить событие");
        }
    }

    @Override
    public void updateEvent(long orgId, long eventId, EventRequest request) {
        try {
            EventEntity event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new EventNotFoundException("Событие не найдено"));
            long realOrgId = event.getOrganization().getId();
            if (realOrgId != orgId) {
                log.error("Организатор {}, которому принадлежит событие, не соответствует указанному организатору {}",
                        realOrgId, orgId);
                throw new OrganizationMismatchException("Организатор, которому принадлежит событие, не соответствует указанному организатору");
            }
            event.setTitle(request.title());
            event.setDescription(request.description());
            event.setTimeStart(request.timeStart());
            event.setTimeEnd(request.timeEnd());
            event.setCity(request.city());
            event.setAddress(request.address());
            event.setImgId(request.imgId());
            event.setCategory(CategoryEventEntity.builder().id(request.categoryId()).build());
            eventRepository.save(event);
        } catch (DataAccessException e) {
            log.error("Не удалось сохранить изменения для события {}: {}", eventId, e.getMessage());
            throw new DataBaseException("Не удалось сохранить изменения для события", e);
        }
    }

    @Override
    public void checkOrgMismatch(long orgId, long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Событие не найдено"));
        if (event.getOrganization().getId() != orgId) {
            log.error("Организатор {} не соответствует указанному организатору {}", event.getOrganization().getId(), orgId);
            throw new OrganizationMismatchException("Организатор не соответствует указанному организатору");
        }
    }

    @Override
    public String getEventTitleById(long eventId) {
        return eventRepository.findTitleById(eventId);
    }

}
