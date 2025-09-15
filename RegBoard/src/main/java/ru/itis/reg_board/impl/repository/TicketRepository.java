package ru.itis.reg_board.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.reg_board.impl.model.TicketEntity;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    Optional<TicketEntity> findByEvent_Id(long eventId);

    @Query("SELECT t.event.organization.id FROM TicketEntity t WHERE t.id = :ticketId")
    Long findOrgIdByTicketId(long ticketId);

}
