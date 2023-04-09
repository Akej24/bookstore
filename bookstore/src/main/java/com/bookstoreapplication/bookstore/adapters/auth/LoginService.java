package com.bookstoreapplication.bookstore.adapters.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Validated
@AllArgsConstructor
class LoginService {

    private RedisTemplate<String, String> template;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecuredUserRepository securedUserRepository;
    private final JwtManager jwtManager;

    String loginUser(@Valid LoginRequest request){

        SecuredUser user = securedUserRepository.findByUserEmailEmail(request.getEmail().getEmail()).orElseThrow( () -> {
            log.warn("Not successfully logged in");
            throw new IllegalArgumentException("Not successfully logged in");
        });
        user.checkPasswordsMatch(request.getPassword(), bCryptPasswordEncoder);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail().getEmail(), request.getPassword().getPassword()));

        String jwtToken = jwtManager.generateJwtToken(user);
        String userId = String.valueOf(user.getUserId());

        template.opsForValue().set(jwtToken, userId);
        template.boundValueOps(jwtToken).expire(3600, TimeUnit.SECONDS);
        log.info("User successfully logged in - created jwt token");
        return jwtToken;
    }

}
