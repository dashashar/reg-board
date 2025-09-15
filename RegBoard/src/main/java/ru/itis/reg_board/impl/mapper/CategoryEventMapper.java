package ru.itis.reg_board.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.itis.reg_board.api.dto.response.CategoryEventResponse;
import ru.itis.reg_board.impl.model.CategoryEventEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryEventMapper {

    List<CategoryEventResponse> toResponseList(List<CategoryEventEntity> entityList);
}
