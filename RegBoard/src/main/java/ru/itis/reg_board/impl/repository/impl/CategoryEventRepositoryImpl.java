package ru.itis.reg_board.impl.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.reg_board.impl.exception.DataBaseException;
import ru.itis.reg_board.impl.model.CategoryEventEntity;
import ru.itis.reg_board.impl.repository.CategoryEventRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryEventRepositoryImpl implements CategoryEventRepository {

    private final EntityManager em;

    @Override
    public List<CategoryEventEntity> getAll() {
        try{
            return em.createQuery("SELECT c FROM CategoryEventEntity c", CategoryEventEntity.class)
                    .getResultList();
        } catch (PersistenceException e){
            log.error(e.getMessage());
            throw new DataBaseException("Не удалось получить категории мероприятий", e);
        }
    }

    @Override
    public CategoryEventEntity findById(long id) {
        try{
            return em.createQuery("SELECT c FROM CategoryEventEntity c WHERE c.id = :id", CategoryEventEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e){
            log.error(e.getMessage());
            throw new DataBaseException("Не удалось получить категорию мероприятия", e);
        }
    }
}
