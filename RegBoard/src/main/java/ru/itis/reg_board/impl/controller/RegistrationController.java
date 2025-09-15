package ru.itis.reg_board.impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.itis.reg_board.api.api.api.RegistrationApi;
import ru.itis.reg_board.api.dto.request.FieldAnswerRequest;
import ru.itis.reg_board.api.dto.response.*;
import ru.itis.reg_board.impl.security.details.AccountUserDetails;
import ru.itis.reg_board.impl.service.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class RegistrationController implements RegistrationApi {

    private final RegistrationService regService;
    private final EventService eventService;

    @Override
    public String getAllEvents(Short categoryId, int page, int size, Model model) {
        Page<EventBriefResponse> eventsPage = regService.getAllEvents(categoryId, page, size);
        model.addAttribute("events", eventsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", eventsPage.getTotalPages());
        model.addAttribute("categories", eventService.getAllCategoriesEvent());
        model.addAttribute("selectedCategory", categoryId);
        return "event/events_list";
    }

    @Override
    public String getEventDetails(long eventId, AccountUserDetails userDetails, Model model) {
        Long accountId = userDetails.accountId();
        EventDetailsResponse eventDetails = regService.getEventDetails(accountId, eventId);
        model.addAttribute("eventId", String.valueOf(eventId));
        model.addAttribute("details", eventDetails);
        return "event/event_details";
    }

    @Override
    public String registerForEvent(long eventId, List<FieldAnswerRequest> answers, AccountUserDetails userDetails) {
        Long accountId = userDetails.accountId();
        regService.createRegistration(accountId, eventId, answers);
        return "redirect:/event/%s".formatted(eventId);
    }

    @Override
    public String getEventsAccountHasRegistration(AccountUserDetails userDetails, Model model) {
        Long accountId = userDetails.accountId();
        List<EventBriefResponse> events = regService.getAllEventsAccountHasRegistration(accountId);
        model.addAttribute("events", events);
        return "account/registration_account";
    }

    @Override
    public ResponseEntity<ConfirmationRegistrationResponse> confirmRegistration(UUID regId) {
        ConfirmationRegistrationResponse confirmation = regService.confirmRegistration(regId);
        return ResponseEntity
                .status(confirmation.httpStatusCode())
                .body(confirmation);
    }
}
