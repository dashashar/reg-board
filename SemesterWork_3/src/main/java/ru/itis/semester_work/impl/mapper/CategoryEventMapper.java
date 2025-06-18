package ru.itis.semester_work.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.itis.semester_work.api.dto.response.CategoryEventResponse;
import ru.itis.semester_work.impl.model.CategoryEventEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryEventMapper {

    List<CategoryEventResponse> toResponseList(List<CategoryEventEntity> entityList);
}
