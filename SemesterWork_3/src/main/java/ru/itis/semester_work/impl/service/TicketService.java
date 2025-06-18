package ru.itis.semester_work.impl.service;

import ru.itis.semester_work.api.dto.request.TicketRequest;
import ru.itis.semester_work.api.dto.response.TicketResponse;

public interface TicketService {

    void createTicket(TicketRequest request, long orgId, long eventId);

    TicketResponse getTicket(long ticketId);

    TicketResponse getTicketByEventId(long eventId);

    void sellTicket(long eventId);

    void updateTicket(TicketRequest request, long ticketId, long orgId);
}
