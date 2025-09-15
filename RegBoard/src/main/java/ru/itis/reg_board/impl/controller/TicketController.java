package ru.itis.reg_board.impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.itis.reg_board.api.api.api.TicketApi;
import ru.itis.reg_board.api.dto.request.TicketRequest;
import ru.itis.reg_board.api.dto.response.TicketResponse;
import ru.itis.reg_board.impl.service.TicketService;
import ru.itis.reg_board.impl.util.ValidationUtil;

@Controller
@RequiredArgsConstructor
public class TicketController implements TicketApi {

    private final TicketService ticketService;

    @Override
    public String showCreateTicketForm(long orgId, long eventId, Model model) {
        model.addAttribute("orgId", String.valueOf(orgId));
        model.addAttribute("eventId", String.valueOf(eventId));
        model.addAttribute("mode", "create");
        return "ticket/ticket_create";
    }

    @Override
    public String createTicket(long orgId, long eventId, TicketRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orgId", String.valueOf(orgId));
            model.addAttribute("eventId", String.valueOf(eventId));
            model.addAttribute("mode", "create");
            model.addAttribute("errors", ValidationUtil.getValidationErrors(bindingResult));
            model.addAttribute("ticket", request);
            return "ticket/ticket_create";
        }
        ticketService.createTicket(request, orgId, eventId);
        return "redirect:/org/%s/event/%s/form".formatted(orgId, eventId);
    }

    @Override
    public String showEditTicketForm(long orgId, long ticketId, Model model) {
        TicketResponse ticket = ticketService.getTicket(ticketId);
        model.addAttribute("orgId", String.valueOf(orgId));
        model.addAttribute("ticketId", String.valueOf(ticketId));
        model.addAttribute("ticket", ticket);
        return "ticket/ticket_update";
    }

    @Override
    public String updateTicket(long orgId, long ticketId, TicketRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orgId", String.valueOf(orgId));
            model.addAttribute("ticketId", String.valueOf(ticketId));
            model.addAttribute("errors", ValidationUtil.getValidationErrors(bindingResult));
            model.addAttribute("ticket", request);
            return "ticket/ticket_update";
        }
        ticketService.updateTicket(request, ticketId, orgId);
        return "redirect:/org/%s/event".formatted(orgId);
    }
}
