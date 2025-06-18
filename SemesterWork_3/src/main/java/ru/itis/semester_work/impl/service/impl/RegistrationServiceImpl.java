package ru.itis.semester_work.impl.service.impl;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.semester_work.api.dto.request.FieldAnswerRequest;
import ru.itis.semester_work.api.dto.response.*;
import ru.itis.semester_work.impl.exception.DataBaseException;
import ru.itis.semester_work.impl.exception.bad_request.IncorrectDataException;
import ru.itis.semester_work.impl.exception.conflict.RegistrationAlreadyExistsException;
import ru.itis.semester_work.impl.mapper.EventMapper;
import ru.itis.semester_work.impl.mapper.RegistrationMapper;
import ru.itis.semester_work.impl.model.RegistrationEntity;
import ru.itis.semester_work.impl.model.enums.RegistrationStatus;
import ru.itis.semester_work.impl.repository.FieldAnswerRepository;
import ru.itis.semester_work.impl.repository.RegistrationRepository;
import ru.itis.semester_work.impl.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final EventService eventService;
    private final TicketService ticketService;
    private final FormFieldService formService;
    private final QrGenerationService qrService;

    private final EventMapper eventMapper;
    private final RegistrationMapper regMapper;
    private final RegistrationRepository regRepository;
    private final FieldAnswerRepository answerRepository;

    @Override
    public Page<EventBriefResponse> getAllEvents(Short categoryId, int page, int size) {
        if (categoryId != null) {
            return eventService.getAllEventsByCategory(categoryId,
                    PageRequest.of(page - 1, size, Sort.by("timeStart").ascending()));
        } else {
            return eventService.getAllEvents(
                    PageRequest.of(page - 1, size, Sort.by("timeStart").ascending()));
        }
    }

    @Override
    @Transactional
    public void createRegistration(long accountId, long eventId, List<FieldAnswerRequest> answers) {
        try {
            if (regRepository.findByAccount_IdAndEvent_Id(accountId, eventId).isPresent()) {
                log.error("Аккаунт {} уже зарегистрирован на событие {}", accountId, eventId);
                throw new RegistrationAlreadyExistsException("Аккаунт уже зарегистрирован на событие");
            }
            RegistrationEntity savedRegistration = regRepository.save(regMapper.toEntity(accountId, eventId));
            ticketService.sellTicket(eventId);
            for (FieldAnswerRequest answer : answers) {
                answerRepository.save(regMapper.toEntity(answer, savedRegistration));
            }
            qrService.generateAndSaveQrCodeForRegistration(savedRegistration.getId());
        } catch (DataIntegrityViolationException e) {
            log.error("Введены некорректные данные, поэтому была нарушена целостность базы данных: {}", e.getMessage());
            throw new IncorrectDataException("Введены некорректные данные");
        } catch (DataAccessException e) {
            log.error("Не удалось создать регистрацию на событие {}: {}", eventId, e.getMessage());
            throw new DataBaseException("Не удалось создать регистрацию на событие");
        }
    }

    @Override
    public EventDetailsResponse getEventDetails(long accountId, long eventId) {
        EventDetailsResponse eventDetails = eventService.getEventWithDetails(eventId);
        Optional<RegistrationEntity> regOpt = regRepository.findByAccount_IdAndEvent_Id(accountId, eventId);
        if (regOpt.isPresent()) {
            if (RegistrationStatus.REGISTERED.equals(regOpt.get().getStatus())) {
                eventDetails.setRegistrationId(regOpt.get().getId());
            }
            eventDetails.setRegistrationIsOpen(false);
            eventDetails.setAccountHasReg(true);
            return eventDetails;
        }
        if (isTicketSale(eventDetails.getTicket())) {
            List<FieldResponse> fields = formService.getFormFields(eventId);
            eventDetails.setRegistrationIsOpen(true);
            eventDetails.setAccountHasReg(false);
            eventDetails.setFields(fields);
            return eventDetails;
        }
        eventDetails.setRegistrationIsOpen(false);
        eventDetails.setAccountHasReg(false);
        return eventDetails;
    }

    @Override
    public List<EventBriefResponse> getAllEventsAccountHasRegistration(long accountId) {
        List<Tuple> tuples = regRepository.findEventsRegistrationByAccountId(accountId);
        return tuples.stream()
                .map(eventMapper::toEventBriefResponse)
                .toList();
    }

    @Override
    @Transactional
    public RegistrationTableResponse getAllRegistrationsByEvent(long orgId, long eventId) {
        eventService.checkOrgMismatch(orgId, eventId);
        String eventTitle = eventService.getEventTitleById(eventId);
        List<FieldResponse> fields = formService.getFormFields(eventId);
        List<RegistrationEntity> registrations = regRepository.findRegistrationsWithAnswersByEventId(eventId);
        return regMapper.toResponse(eventTitle, fields, registrations);
    }

    @Override
    public ConfirmationRegistrationResponse confirmRegistration(UUID registrationId) {
        try {
            Optional<RegistrationEntity> regOpt = regRepository.findById(registrationId);
            if (regOpt.isEmpty()){
                log.error("Регистрация {} не найдена", registrationId);
                return new ConfirmationRegistrationResponse(404, "FAILED",
                        "Регистрация не найдена", registrationId);
            }
            RegistrationEntity reg = regOpt.get();
            if (RegistrationStatus.CHECKED_IN.equals(reg.getStatus())) {
                log.warn("Регистрация {} ранее уже была подтверждена", registrationId);
                return new ConfirmationRegistrationResponse(409, "FAILED",
                        "Регистрация ранее уже была подтверждена", registrationId);
            }
            reg.setStatus(RegistrationStatus.CHECKED_IN);
            regRepository.save(reg);
            qrService.deleteQrCodeForRegistration(reg.getId());
            return new ConfirmationRegistrationResponse(200, "SUCCESS",
                    "Участник отмечен", registrationId);
        } catch (DataAccessException e) {
            log.error("Не удалось подтвердить регистрацию {}: {}", registrationId, e.getMessage());
            return new ConfirmationRegistrationResponse(500, "FAILED",
                    "Не удалось подтвердить регистрацию", registrationId);
        }
    }

    private boolean isTicketSale(TicketResponse ticket) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(ticket.salesStart()) && now.isBefore(ticket.salesEnd())
                && (ticket.soldTickets() < ticket.totalTickets());
    }
}
