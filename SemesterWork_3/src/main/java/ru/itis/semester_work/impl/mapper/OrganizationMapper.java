package ru.itis.semester_work.impl.mapper;

import jakarta.persistence.Tuple;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.itis.semester_work.api.dto.request.OrganizationRequest;
import ru.itis.semester_work.api.dto.response.AccountResponse;
import ru.itis.semester_work.api.dto.response.OrganizationAdminResponse;
import ru.itis.semester_work.api.dto.response.OrganizationBriefResponse;
import ru.itis.semester_work.api.dto.response.OrganizationResponse;
import ru.itis.semester_work.impl.model.AccountEntity;
import ru.itis.semester_work.impl.model.OrganizationEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper {

    OrganizationBriefResponse toEntity(OrganizationEntity entity);

    AccountResponse toResponse(AccountEntity account);

    OrganizationResponse toResponse(OrganizationEntity entity);

    @Mapping(target = "admins", source = "accounts")
    OrganizationAdminResponse toOrgAdminResponse(OrganizationEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    OrganizationEntity toEntity(OrganizationRequest request);

    default OrganizationBriefResponse toOrgBriefResponse(Tuple tuple){
        if (tuple == null) {
            return null;
        }
        return new OrganizationBriefResponse(
                tuple.get("id", Long.class),
                tuple.get("name", String.class)
        );
    }

}
