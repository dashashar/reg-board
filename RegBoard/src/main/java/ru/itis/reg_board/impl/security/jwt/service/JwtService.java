package ru.itis.reg_board.impl.security.jwt.service;

import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    String generateToken(Long id, String email);

    String extractUsername(String token);

    Long extractAccountId(String token);
}
