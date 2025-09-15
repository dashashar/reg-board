package ru.itis.reg_board.impl.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.itis.reg_board.api.dto.request.TicketRequest;
import ru.itis.reg_board.api.dto.response.TicketResponse;
import ru.itis.reg_board.impl.exception.DataBaseException;
import ru.itis.reg_board.impl.exception.bad_request.AvailableNumberTicketsException;
import ru.itis.reg_board.impl.exception.conflict.TicketAlreadyExistsException;
import ru.itis.reg_board.impl.exception.bad_request.OrganizationMismatchException;
import ru.itis.reg_board.impl.exception.not_found.TicketNotFoundException;
import ru.itis.reg_board.impl.mapper.TicketMapper;
import ru.itis.reg_board.impl.model.EventEntity;
import ru.itis.reg_board.impl.model.TicketEntity;
import ru.itis.reg_board.impl.repository.TicketRepository;
import ru.itis.reg_board.impl.service.EventService;
import ru.itis.reg_board.impl.service.TicketService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final EventService eventService;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public void createTicket(TicketRequest request, long orgId, long eventId) {
        try {
            eventService.checkOrgMismatch(orgId, eventId);
            Optional<TicketEntity> existingTicket = ticketRepository.findByEvent_Id(eventId);
            if (existingTicket.isPresent()) {
                log.error("Для события id: {} уже существует билет", eventId);
                throw new TicketAlreadyExistsException("Для события уже существует билет", existingTicket.get().getId());
            }
            TicketEntity newTicket = ticketMapper.toEntity(request);
            newTicket.setSoldTickets(0);
            newTicket.setEvent(EventEntity.builder().id(eventId).build());
            ticketRepository.save(newTicket);
        } catch(DataAccessException e) {
            log.error("Не удалось создать билет на событие {}: {}", eventId, e.getMessage());
            throw new DataBaseException("Не удалось создать билет", e);
        }
    }

    @Override
    public TicketResponse getTicket(long ticketId) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Билет %d не найден".formatted(ticketId)));
        return ticketMapper.toResponse(ticket);
    }

    @Override
    public TicketResponse getTicketByEventId(long eventId) {
        TicketEntity ticket = ticketRepository.findByEvent_Id(eventId)
                .orElseThrow(() -> new TicketNotFoundException("Билет на событие %d не найден".formatted(eventId)));
        return ticketMapper.toResponse(ticket);
    }

    @Override
    public void sellTicket(long eventId) {
        TicketEntity ticket = ticketRepository.findByEvent_Id(eventId)
                .orElseThrow(() -> new TicketNotFoundException("Билет на событие %d не найден".formatted(eventId)));
        ticket.setSoldTickets(ticket.getSoldTickets() + 1);
        ticketRepository.save(ticket);
    }

    @Override
    public void updateTicket(TicketRequest request, long ticketId, long orgId) {
        try {
            TicketEntity existingTicket = ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new TicketNotFoundException("Билет %d не найден".formatted(ticketId)));
            Long originalOrgId = ticketRepository.findOrgIdByTicketId(ticketId);
            if (orgId != originalOrgId) {
                log.error("Организатор {}, которому принадлежит билет, не соответствует указанному организатору {} ",
                        originalOrgId, orgId);
                throw new OrganizationMismatchException(
                        "Организатор, которому принадлежит билет, не соответствует указанному организатору");
            }
            if (request.totalTickets() < existingTicket.getSoldTickets()) {
                throw new AvailableNumberTicketsException(
                        "Общее количество билетов не может быть меньше проданных билетов");
            }
            existingTicket.setName(request.name());
            existingTicket.setPrice(request.price());
            existingTicket.setTotalTickets(request.totalTickets());
            existingTicket.setSalesStart(request.salesStart());
            existingTicket.setSalesEnd(request.salesEnd());
            ticketRepository.save(existingTicket);
        } catch(DataAccessException e) {
            log.error("Не удалось обновить информацию по билету {}: {}", ticketId, e.getMessage());
            throw new DataBaseException("Не удалось обновить информацию по билету", e);
        }
    }

}
