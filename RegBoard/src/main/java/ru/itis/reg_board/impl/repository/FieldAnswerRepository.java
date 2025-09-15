package ru.itis.reg_board.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.reg_board.impl.model.FieldAnswerEntity;

public interface FieldAnswerRepository extends JpaRepository<FieldAnswerEntity, Long> {
}
