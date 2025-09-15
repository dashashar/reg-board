package ru.itis.reg_board.impl.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.reg_board.api.dto.request.SignUpRequest;
import ru.itis.reg_board.api.dto.response.AccountResponse;
import ru.itis.reg_board.impl.mapper.AccountMapper;
import ru.itis.reg_board.impl.model.AccountEntity;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {

    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountEntity toEntity(SignUpRequest request) {
        if (request == null) {
            return null;
        }

        return AccountEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .hashPassword(passwordEncoder.encode(request.password()))
                .build();
    }

    @Override
    public AccountResponse toResponse(AccountEntity entity) {
        if (entity == null){
            return null;
        }
        return new AccountResponse(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail());
    }
}
