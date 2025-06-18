package ru.itis.semester_work.impl.security.jwt.service;

import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    String generateToken(Long id, String email);

    String extractUsername(String token);

    Long extractAccountId(String token);
}
