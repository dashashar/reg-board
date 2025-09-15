package ru.itis.reg_board.impl.repository;

import ru.itis.reg_board.impl.model.CategoryEventEntity;

import java.util.List;

public interface CategoryEventRepository {

    List<CategoryEventEntity> getAll();

    CategoryEventEntity findById(long id);
}
