package com.bookstoreapplication.bookstore.auth;

import com.bookstoreapplication.bookstore.auth.exception.JwtNofFoundInRequestHeaderException;
import com.bookstoreapplication.bookstore.auth.exception.UserEmailHasNotBeenFoundException;
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
            throw new JwtNofFoundInRequestHeaderException();
        }
        String jwt = authHeader.substring(7);
        String email = jwtManager.getUsernameFromToken(jwt);
        SecuredUser securedUser = securedUserRepository.findByUserEmailEmail(email)
                .orElseThrow(UserEmailHasNotBeenFoundException::new);
        return securedUser.getUserId();

    }

}
