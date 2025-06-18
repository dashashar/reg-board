package ru.itis.semester_work.impl.mapper;

import ru.itis.semester_work.api.dto.request.SignUpRequest;
import ru.itis.semester_work.api.dto.response.AccountResponse;
import ru.itis.semester_work.impl.model.AccountEntity;

public interface AccountMapper {

    AccountEntity toEntity(SignUpRequest request);

    AccountResponse toResponse(AccountEntity entity);

}
