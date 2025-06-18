package ru.itis.semester_work.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.itis.semester_work.api.dto.request.TicketRequest;
import ru.itis.semester_work.api.dto.response.TicketResponse;
import ru.itis.semester_work.impl.model.TicketEntity;

import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "soldTickets", ignore = true)
    @Mapping(target = "event", ignore = true)
    TicketEntity toEntity(TicketRequest request);

    TicketResponse toResponse(TicketEntity entity);

    default Optional<TicketResponse> toResponse(Optional<TicketEntity> entity) {
        return entity.map(this::toResponse);
    }

}
