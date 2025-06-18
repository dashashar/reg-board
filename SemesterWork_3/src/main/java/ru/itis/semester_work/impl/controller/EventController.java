package ru.itis.semester_work.impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.itis.semester_work.api.api.EventApi;
import ru.itis.semester_work.api.dto.request.EventRequest;
import ru.itis.semester_work.api.dto.response.EventBriefResponse;
import ru.itis.semester_work.api.dto.response.EventResponse;
import ru.itis.semester_work.api.dto.response.RegistrationTableResponse;
import ru.itis.semester_work.impl.service.EventService;
import ru.itis.semester_work.impl.service.RegistrationService;
import ru.itis.semester_work.impl.util.ValidationUtil;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventController implements EventApi {

    private final EventService eventService;
    private final RegistrationService regService;

    @Override
    public String getAllOrgEvents(long orgId, Model model) {
        List<EventBriefResponse> events = eventService.getAllOrgEventsBrief(orgId);
        model.addAttribute("events", events);
        model.addAttribute("orgId", String.valueOf(orgId));
        return "org/all_events";
    }

    @Override
    public String showCreateEventForm(long orgId, Model model) {
        model.addAttribute("orgId", String.valueOf(orgId));
        model.addAttribute("categories", eventService.getAllCategoriesEvent());
        return "event/event_create";
    }

    @Override
    public String createEvent(long orgId, EventRequest request, BindingResult bindingResult, Model model) {
        model.addAttribute("orgId", String.valueOf(orgId));
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", ValidationUtil.getValidationErrors(bindingResult));
            model.addAttribute("event", request);
            model.addAttribute("categories", eventService.getAllCategoriesEvent());
            return "event/event_create";
        }
        Long eventId = eventService.createEvent(orgId, request);
        return "redirect:/org/%s/event/%s/ticket/create".formatted(orgId, eventId);
    }

    @Override
    public String showUpdateEventForm(long orgId, long eventId, Model model) {
        EventResponse event = eventService.getEvent(eventId);
        model.addAttribute("orgId", String.valueOf(orgId));
        model.addAttribute("eventId", String.valueOf(eventId));
        model.addAttribute("categories", eventService.getAllCategoriesEvent());
        model.addAttribute("event", event);
        return "event/event_update";
    }

    @Override
    public String updateEvent(long orgId, long eventId, EventRequest request, BindingResult bindingResult, Model model) {
        model.addAttribute("orgId", String.valueOf(orgId));
        model.addAttribute("eventId", String.valueOf(eventId));
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", ValidationUtil.getValidationErrors(bindingResult));
            model.addAttribute("event", request);
            model.addAttribute("categories", eventService.getAllCategoriesEvent());
            return "event/event_update";
        }
        eventService.updateEvent(orgId, eventId, request);
        return "redirect:/org/%s/event".formatted(orgId);
    }

    @Override
    public String deleteEvent(long orgId, long eventId) {
        eventService.deleteEvent(orgId, eventId);
        return "redirect:/org/%s/event".formatted(orgId);
    }

    @Override
    public String getAllRegistrationsForEvent(long orgId, long eventId, Model model) {
        RegistrationTableResponse table = regService.getAllRegistrationsByEvent(orgId, eventId);
        model.addAttribute("table", table);
        return "event/all_registrations";
    }

}
