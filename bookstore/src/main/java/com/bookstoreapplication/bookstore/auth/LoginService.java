package com.bookstoreapplication.bookstore.auth;

import com.bookstoreapplication.bookstore.user.SimpleUserQueryDto;
import com.bookstoreapplication.bookstore.user.UserFacade;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Service
@Validated
@AllArgsConstructor
class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private RedisTemplate<String, String> template;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;
    private final UserFacade userFacade;

    String loginUser(@Valid LoginRequest request){

        SimpleUserQueryDto existingUser = userFacade.findByUsername(request.getUsername());
        checkPasswordsMatch(request, existingUser);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));

        String jwtToken = jwtManager.generateJwtToken(existingUser);
        String userId = String.valueOf(existingUser.getUserId());

        template.opsForValue().set(jwtToken, userId);
        template.boundValueOps(jwtToken).expire(3600, TimeUnit.SECONDS);
        LOGGER.info("User with e-mail {} successfully logged in - created jwt token", request.getUsername());
        return jwtToken;
    }

    private void checkPasswordsMatch(LoginRequest request, SimpleUserQueryDto existingUser) {
        String passwordFromDatabase = existingUser.getPassword();
        String passwordFromRequest = request.getPassword();
        if(!bCryptPasswordEncoder.matches(passwordFromRequest, passwordFromDatabase)){
            LOGGER.warn("Unsuccessfully logged in - invalid password for user with e-mail {}", request.getUsername());
            throw new IllegalArgumentException("Invalid password for user with given e-mail");
        }
    }

}
