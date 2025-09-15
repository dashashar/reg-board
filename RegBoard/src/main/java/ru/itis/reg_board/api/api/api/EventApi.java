package ru.itis.reg_board.api.api.api;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.reg_board.api.dto.request.EventRequest;

@RequestMapping("/org/{orgId}/event")
public interface EventApi {

    @GetMapping
    String getAllOrgEvents(@PathVariable("orgId") long orgId, Model model);

    @GetMapping("/create")
    String showCreateEventForm(@PathVariable("orgId") long orgId,
                               Model model);

    @PostMapping("/create")
    String createEvent(@PathVariable("orgId") long orgId,
                       @Valid @ModelAttribute("request") EventRequest request,
                       BindingResult bindingResult,
                       Model model);

    @GetMapping("/{eventId}/update")
    String showUpdateEventForm(@PathVariable("orgId") long orgId,
                               @PathVariable("eventId") long eventId,
                               Model model);

    @PostMapping("/{eventId}/update")
    String updateEvent(@PathVariable("orgId") long orgId,
                       @PathVariable("eventId") long eventId,
                       @Valid @ModelAttribute("request") EventRequest request,
                       BindingResult bindingResult,
                       Model model);

    @PostMapping("/{eventId}/delete")
    String deleteEvent(@PathVariable("orgId") long orgId,
                       @PathVariable("eventId") long eventId);

    @GetMapping("/{eventId}/registration")
    String getAllRegistrationsForEvent(@PathVariable long orgId,
                                       @PathVariable("eventId") long eventId,
                                       Model model);
}
