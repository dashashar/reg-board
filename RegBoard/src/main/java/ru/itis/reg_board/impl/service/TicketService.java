package ru.itis.reg_board.impl.service;

import ru.itis.reg_board.api.dto.request.TicketRequest;
import ru.itis.reg_board.api.dto.response.TicketResponse;

public interface TicketService {

    void createTicket(TicketRequest request, long orgId, long eventId);

    TicketResponse getTicket(long ticketId);

    TicketResponse getTicketByEventId(long eventId);

    void sellTicket(long eventId);

    void updateTicket(TicketRequest request, long ticketId, long orgId);
}
