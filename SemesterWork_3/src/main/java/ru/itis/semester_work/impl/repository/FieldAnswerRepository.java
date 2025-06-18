package ru.itis.semester_work.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.semester_work.impl.model.FieldAnswerEntity;

public interface FieldAnswerRepository extends JpaRepository<FieldAnswerEntity, Long> {
}
