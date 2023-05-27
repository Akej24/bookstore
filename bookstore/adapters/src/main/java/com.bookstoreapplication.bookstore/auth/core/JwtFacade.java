package com.bookstoreapplication.bookstore.auth.core;

import com.bookstoreapplication.bookstore.auth.exception.JwtNofFoundInRequestHeaderException;
import com.bookstoreapplication.bookstore.auth.exception.UserEmailHasNotBeenFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@AllArgsConstructor
public class JwtFacade {

    private final JwtManager jwtManager;
    private final SecuredUserRepository securedUserRepository;

    public long extractUserId(HttpServletRequest request){
        String jwt = extractJwtFromRequest(request);
        String email = jwtManager.getUsernameFromToken(jwt);
        return securedUserRepository.findByUserEmailEmail(email)
                .orElseThrow(UserEmailHasNotBeenFoundException::new).getUserId();
    }

    public List<GrantedAuthority> extractUserRole(HttpServletRequest request){
        String jwt = extractJwtFromRequest(request);
        return jwtManager.getUserRoleFromToken(jwt);
    }

    private static String extractJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new JwtNofFoundInRequestHeaderException();
        }
        return authHeader.substring(7);
    }
}
