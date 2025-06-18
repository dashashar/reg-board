package ru.itis.semester_work.impl.repository;

import ru.itis.semester_work.impl.model.CategoryEventEntity;

import java.util.List;

public interface CategoryEventRepository {

    List<CategoryEventEntity> getAll();

    CategoryEventEntity findById(long id);
}
