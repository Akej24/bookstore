package com.bookstoreapplication.bookstore.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
@AllArgsConstructor
class LogoutService implements LogoutHandler {

    private final RedisTemplate<String, String> template;
    private final JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) return;

        long userId = jwtService.extractUserIdFromRequest(request);
        template.delete("user:"+userId);
        SecurityContextHolder.clearContext();
        log.info("Successfully logged out");
    }
}
