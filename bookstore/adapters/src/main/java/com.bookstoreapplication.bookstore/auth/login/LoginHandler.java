package com.bookstoreapplication.bookstore.auth.login;

import com.bookstoreapplication.bookstore.auth.core.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Validated
@AllArgsConstructor
class LoginHandler {

    private final RedisTemplate<String, String> template;
    private final AuthenticationManager authenticationManager;
    private final LoginService loginService;

    @Transactional
    String loginUser(@Valid LoginCommand request){
        String userId = loginService.verifyUserPasswordMatches(request.email(), request.password());
        String jwt = loginService.generateJwt(request.email());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email().getEmail(), request.password().getPassword()));

        template.opsForValue().set("user:" + userId, jwt);
        template.expire("user:" + userId, 24, TimeUnit.HOURS);
        log.info("User successfully logged in - created jwt token");
        return jwt;
    }
}
