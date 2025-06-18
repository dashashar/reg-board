package ru.itis.semester_work.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.semester_work.impl.model.FieldEntity;

import java.util.List;

public interface FormFieldRepository extends JpaRepository<FieldEntity, Long> {

    List<FieldEntity> findByEvent_IdOrderByPosition(Long eventId);

}
