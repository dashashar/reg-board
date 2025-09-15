package ru.itis.reg_board.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.itis.reg_board.api.dto.request.FieldRequest;
import ru.itis.reg_board.api.dto.response.FieldResponse;
import ru.itis.reg_board.impl.model.FieldEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FieldMapper {

    @Mapping(target = "event", ignore = true)
    @Mapping(target = "answers", ignore = true)
    FieldEntity toEntity(FieldRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(entity.getId()))")
    FieldResponse toResponse(FieldEntity entity);

    List<FieldResponse> toResponses(List<FieldEntity> fields);

}
