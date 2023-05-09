package com.bookstoreapplication.bookstore.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtManager jwtManager;
    private final SecuredUserRepository securedUserRepository;

    public long extractUserIdFromRequest(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("JWT not found in request header");
        }
        String jwt = authHeader.substring(7);
        String email = jwtManager.getUsernameFromToken(jwt);
        SecuredUser securedUser = securedUserRepository.findByUserEmailEmail(email).orElseThrow( () ->
            new IllegalArgumentException("User with given email does not exist"));
        return securedUser.getUserId();

    }

}
