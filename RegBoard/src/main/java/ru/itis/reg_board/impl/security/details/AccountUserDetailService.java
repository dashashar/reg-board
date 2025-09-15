package ru.itis.reg_board.impl.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.itis.reg_board.impl.security.jwt.service.JwtService;

@Service
@RequiredArgsConstructor
public class AccountUserDetailService implements UserDetailsService {

    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String token) {
        Long accountId = jwtService.extractAccountId(token);
        String email = jwtService.extractUsername(token);

        return new AccountUserDetails(accountId, email);
    }
}
