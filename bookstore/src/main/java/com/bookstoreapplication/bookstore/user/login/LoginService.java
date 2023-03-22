package com.bookstoreapplication.bookstore.user.login;

import com.bookstoreapplication.bookstore.config.TokenManager;
import com.bookstoreapplication.bookstore.user.account.User;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
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

    private final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private RedisTemplate<String, String> template;
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;

    String loginUser(@Valid LoginRequest request){

        User existingUser = userRepository.findByUsername(request.getUsername()).orElseThrow(()->{
            logger.warn("Unsuccessfully logged in - user with given email does not exist");
            throw new IllegalArgumentException("User with given email does not exist");
        });

        String passwordFromDatabase = existingUser.getPassword();
        String passwordFromRequest = request.getPassword();
        if(!bCryptPasswordEncoder.matches(passwordFromRequest, passwordFromDatabase)){
            logger.warn("Unsuccessfully logged in - invalid password for user with e-mail {}", request.getUsername());
            throw new IllegalArgumentException("Invalid password for user with given e-mail");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));

        String jwtToken = tokenManager.generateJwtToken(existingUser);
        String userId = String.valueOf(existingUser.getUserId());
        template.opsForHash().put(jwtToken,"User", userId);
        template.boundHashOps(jwtToken).expire(3600, TimeUnit.SECONDS);

        logger.info("User with e-mail {} successfully logged in - created jwt token", request.getUsername());
        return jwtToken;
    }
}
