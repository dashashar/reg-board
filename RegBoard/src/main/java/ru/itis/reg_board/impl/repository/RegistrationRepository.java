package ru.itis.reg_board.impl.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.reg_board.impl.model.RegistrationEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, UUID> {

    Optional<RegistrationEntity> findByAccount_IdAndEvent_Id(long accountId, long eventId);

    @Query("""
            SELECT e.id as id, e.title as title, e.timeStart as timeStart, e.timeEnd as timeEnd, e.city as city,
            e.address as address, t.id as ticketId, t.price as price, e.imgId as imgId
            FROM RegistrationEntity r JOIN r.event e JOIN e.ticket t WHERE r.account.id = :accountId
            ORDER BY e.timeStart DESC""")
    List<Tuple> findEventsRegistrationByAccountId(@Param("accountId") long accountId);

    @Query(""" 
           SELECT DISTINCT r FROM RegistrationEntity r LEFT JOIN FETCH r.fieldAnswers fa
           LEFT JOIN FETCH fa.field f WHERE r.event.id = :eventId ORDER BY r.id, f.position""")
    List<RegistrationEntity> findRegistrationsWithAnswersByEventId(long eventId);

}
