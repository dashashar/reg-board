package ru.itis.reg_board.impl.mapper;

import jakarta.persistence.Tuple;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ru.itis.reg_board.api.dto.request.EventRequest;
import ru.itis.reg_board.api.dto.response.EventBriefResponse;
import ru.itis.reg_board.api.dto.response.EventDetailsResponse;
import ru.itis.reg_board.api.dto.response.EventResponse;
import ru.itis.reg_board.impl.model.CategoryEventEntity;
import ru.itis.reg_board.impl.model.EventEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TicketMapper.class, OrganizationMapper.class})
public abstract class EventMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd MMMM yyyy");

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "registrations", ignore = true)
    @Mapping(target = "fields", ignore = true)
    @Mapping(target = "category", source = "request", qualifiedByName = "mapCategoryRequest")
    public abstract EventEntity toEntity(EventRequest request);

    @Mapping(target = "categoryId", source = "entity", qualifiedByName = "mapCategoryResponse")
    public abstract EventResponse toResponse(EventEntity entity);

    public abstract List<EventResponse> toResponse(List<EventEntity> entities);

    @Mapping(target = "org", source = "organization")
    @Mapping(target = "ticket", source = "ticket")
    @Mapping(target = "registrationIsOpen", ignore = true)
    @Mapping(target = "accountHasReg", ignore = true)
    @Mapping(target = "fields", ignore = true)
    @Mapping(target = "registrationId", ignore = true)
    @Mapping(target = "timeStart", source = "timeStart", qualifiedByName = "mapDateTime")
    @Mapping(target = "timeEnd", source = "timeEnd", qualifiedByName = "mapDateTime")
    public abstract EventDetailsResponse toDetailsResponse(EventEntity event);

    public EventBriefResponse toEventBriefResponse(Tuple tuple) {
        if (tuple == null) {
            return null;
        }
        return new EventBriefResponse(
                tuple.get("id", Long.class).toString(),
                tuple.get("title", String.class),
                mapDateTime((tuple.get("timeStart", LocalDateTime.class))),
                mapDateTime((tuple.get("timeEnd", LocalDateTime.class))),
                tuple.get("city", String.class),
                tuple.get("address", String.class),
                toString(tuple.get("ticketId", Long.class)),
                tuple.get("price", Integer.class),
                tuple.get("imgId", String.class)
        );
    }

    @Named("mapCategoryRequest")
    protected CategoryEventEntity mapCategoryRequest(EventRequest request) {
        if (request.categoryId() == null) {
            return null;
        }
        return CategoryEventEntity.builder()
                .id(request.categoryId())
                .build();
    }

    @Named("mapCategoryResponse")
    protected Short mapCategoryResponse(EventEntity entity) {
        if (entity.getCategory() == null) {
            return null;
        }
        return entity.getCategory().getId();
    }

    @Named("mapDateTime")
    protected String mapDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(FORMATTER) : null;
    }

    protected String toString(Long value) {
        return value != null ? value.toString() : null;
    }

}
