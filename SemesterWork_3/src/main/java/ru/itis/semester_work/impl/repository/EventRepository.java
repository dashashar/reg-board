package ru.itis.semester_work.impl.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.semester_work.impl.model.EventEntity;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query("""
            SELECT e.id as id, e.title as title, e.timeStart as timeStart, e.timeEnd as timeEnd,
            e.city as city, e.address as address, t.id as ticketId, t.price as price, e.imgId as imgId
            FROM EventEntity e LEFT JOIN e.ticket t
            WHERE e.organization.id IN (SELECT o.id FROM OrganizationEntity o WHERE o.id = :orgId)
            ORDER BY e.timeStart DESC
            """)
    List<Tuple> findBriefEventsByOrgId(@Param("orgId") long orgId);

    @Query(value = """
            SELECT e.id as id, e.title as title, e.timeStart as timeStart, e.timeEnd as timeEnd,
            e.city as city, e.address as address, t.id as ticketId, t.price as price, e.imgId as imgId
            FROM EventEntity e JOIN e.ticket t ORDER BY e.timeStart DESC""",
            countQuery = "SELECT COUNT(e) FROM EventEntity e")
    Page<Tuple> findAllBriefEvents(Pageable pageable);

    @Query(value = """
            SELECT e.id as id, e.title as title, e.timeStart as timeStart, e.timeEnd as timeEnd,
            e.city as city, e.address as address, t.id as ticketId, t.price as price, e.imgId as imgId
            FROM EventEntity e JOIN e.ticket t where e.category.id = :categoryId ORDER BY e.timeStart DESC""",
            countQuery = "SELECT COUNT(e) FROM EventEntity e")
    Page<Tuple> findAllBriefEventsByCategory(@Param("categoryId") short categoryId, Pageable pageable);

    @Query("""
            SELECT e FROM EventEntity e JOIN FETCH e.ticket JOIN FETCH e.organization
            WHERE e.id = :eventId
            """)
    Optional<EventEntity> findEventWithTicketAndOrganization(@Param("eventId") long eventId);

    @Query("SELECT e.title FROM EventEntity e WHERE e.id = :eventId")
    String findTitleById(long eventId);

}
