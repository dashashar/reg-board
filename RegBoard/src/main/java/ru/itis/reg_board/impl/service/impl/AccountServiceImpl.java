package ru.itis.reg_board.impl.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.reg_board.api.dto.request.SignUpRequest;
import ru.itis.reg_board.impl.exception.not_found.AccountNotFoundException;
import ru.itis.reg_board.impl.exception.DataBaseException;
import ru.itis.reg_board.impl.exception.conflict.EmailAlreadyExistsException;
import ru.itis.reg_board.impl.exception.InvalidPasswordException;
import ru.itis.reg_board.impl.mapper.AccountMapper;
import ru.itis.reg_board.impl.model.AccountEntity;
import ru.itis.reg_board.impl.repository.AccountRepository;
import ru.itis.reg_board.impl.security.jwt.service.JwtService;
import ru.itis.reg_board.impl.service.AccountService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void createAccount(SignUpRequest request) {
        try {
            if (accountRepository.findByEmail(request.email()).isPresent()) {
                log.warn("Аккаунт с email: {} уже зарегистрирован", request.email());
                throw new EmailAlreadyExistsException(
                        "Аккаунт с email: %s уже зарегистрирован".formatted(request.email()));
            }
            AccountEntity newAccount = accountMapper.toEntity(request);
            accountRepository.save(newAccount);
        } catch (DataAccessException e) {
            log.error("Не удалось создать аккаунт: {}", e.getMessage());
            throw new DataBaseException("Не удалось создать аккаунт", e);
        }
    }

    @Override
    public String signInAccount(String email, String password) {
        AccountEntity account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(
                        "Аккаунт с email: %s не найден".formatted(email)));

        if (!passwordEncoder.matches(password, account.getHashPassword())) {
            log.error("Неккоректный пароль для {}", email);
            throw new InvalidPasswordException("Неккоректный пароль для %s".formatted(email));
        }

        return jwtService.generateToken(account.getId(), email);
    }

}
