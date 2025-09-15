package ru.itis.reg_board.api.api.api;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.reg_board.api.dto.request.TicketRequest;

@RequestMapping("/org/{orgId}")
public interface TicketApi {

    @GetMapping("/event/{eventId}/ticket/create")
    String showCreateTicketForm(@PathVariable("orgId") long orgId,
                                @PathVariable("eventId") long eventId,
                                Model model);

    @PostMapping("/event/{eventId}/ticket/create")
    String createTicket(@PathVariable("orgId") long orgId,
                        @PathVariable("eventId") long eventId,
                        @Valid @ModelAttribute("request") TicketRequest request,
                        BindingResult bindingResult,
                        Model model);

    @GetMapping("/ticket/{ticketId}/update")
    String showEditTicketForm(@PathVariable("orgId") long orgId,
                              @PathVariable("ticketId") long ticketId,
                              Model model);

    @PostMapping("/ticket/{ticketId}/update")
    String updateTicket(@PathVariable("orgId") long orgId,
                        @PathVariable("ticketId") long ticketId,
                        @Valid @ModelAttribute("request") TicketRequest request,
                        BindingResult bindingResult,
                        Model model);

}
