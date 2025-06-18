package ru.itis.semester_work.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.semester_work.impl.model.AccountEntity;

import java.util.Optional;
import java.util.Set;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query(value = "SELECT organization_id FROM organization_account WHERE account_id = :id", nativeQuery = true)
    Set<Long> findAllOrgIdsById(@Param("id") long id);

    Optional<AccountEntity> findByEmail(String email);

}
