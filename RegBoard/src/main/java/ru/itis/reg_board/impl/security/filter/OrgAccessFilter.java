package ru.itis.reg_board.impl.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.reg_board.impl.repository.AccountRepository;
import ru.itis.reg_board.impl.security.details.AccountUserDetails;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrgAccessFilter extends OncePerRequestFilter {

    private final AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();

        if (uri.startsWith(contextPath + "/org/account/") || uri.startsWith(contextPath + "/api/org/account/") ||
                !uri.startsWith(contextPath + "/org/") || !uri.startsWith(contextPath + "/api/org/")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AccountUserDetails userDetails) {
            Long orgId = extractOrgId(request);

            if (orgId == null) {
                log.error("Id организации обязателен: {}", uri);
                response.sendRedirect("%s/account/signIn?organization=IdRequired".formatted(request.getContextPath()));
                return;
            }

            Set<Long> accessOrgIds = accountRepository.findAllOrgIdsById(userDetails.accountId());
            if (!accessOrgIds.contains(orgId)){
                log.warn("Нет доступа к организации с id {} у пользователя с id {}", orgId, userDetails.accountId());
                response.sendRedirect("%s/account/signIn?organization=NoAccess".formatted(request.getContextPath()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private Long extractOrgId(HttpServletRequest request){
        try {
            String path = request.getRequestURI();
            String[] parts = path.split("/");
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("org") && parts.length > i + 1) {
                    try {
                        return Long.parseLong(parts[i + 1]);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
            return null;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
