package ru.itis.semester_work.impl.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.semester_work.api.dto.request.SignUpRequest;
import ru.itis.semester_work.api.dto.response.AccountResponse;
import ru.itis.semester_work.impl.mapper.AccountMapper;
import ru.itis.semester_work.impl.model.AccountEntity;

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
