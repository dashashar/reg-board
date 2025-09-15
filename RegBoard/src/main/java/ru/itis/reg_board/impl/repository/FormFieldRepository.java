package ru.itis.reg_board.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.reg_board.impl.model.FieldEntity;

import java.util.List;

public interface FormFieldRepository extends JpaRepository<FieldEntity, Long> {

    List<FieldEntity> findByEvent_IdOrderByPosition(Long eventId);

}
