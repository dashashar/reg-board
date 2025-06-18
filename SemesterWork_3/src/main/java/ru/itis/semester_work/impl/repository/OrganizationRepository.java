package ru.itis.semester_work.impl.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.semester_work.impl.model.OrganizationEntity;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {

    @Query("SELECT o.id as id, o.name as name FROM OrganizationEntity o JOIN o.accounts a WHERE a.id = :accountId")
    List<Tuple> findBriefOrgByAccountId(@Param("accountId") long accountId);

    @Query("SELECT o FROM OrganizationEntity o LEFT JOIN FETCH o.accounts WHERE o.id = :orgId")
    Optional<OrganizationEntity> findByIdWithAccounts(@Param("orgId") long orgId);

    @Query("SELECT COUNT(o) > 0 FROM OrganizationEntity o JOIN o.accounts a WHERE a.id = :accountId AND o.id = :orgId")
    boolean existsByAccountIdAndOrganizationId(@Param("accountId") long accountId, @Param("orgId") long orgId);

}
