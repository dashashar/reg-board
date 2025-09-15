package ru.itis.reg_board.impl.mapper;

import ru.itis.reg_board.api.dto.request.SignUpRequest;
import ru.itis.reg_board.api.dto.response.AccountResponse;
import ru.itis.reg_board.impl.model.AccountEntity;

public interface AccountMapper {

    AccountEntity toEntity(SignUpRequest request);

    AccountResponse toResponse(AccountEntity entity);

}
