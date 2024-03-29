package com.bookstoreapplication.bookstore.auth.core;

import java.io.IOException;

import com.bookstoreapplication.bookstore.auth.exception.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Profile("!integration")
@Component
@AllArgsConstructor
class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SecuredUserRepository securedUserRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserDetailsService userDetailsService;
    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwtFromHeader = authHeader.substring(7);
        final String username = jwtManager.getUsernameFromToken(jwtFromHeader);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            long userId = securedUserRepository.findByUserEmailEmail(username)
                    .orElseThrow(UsernameNotFoundException::new).getUserId();
            String jwtFromRedis = redisTemplate.opsForValue().get("user:" + userId);
            if (jwtManager.validateJwtToken(jwtFromHeader, userDetails) && jwtFromRedis != null) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
