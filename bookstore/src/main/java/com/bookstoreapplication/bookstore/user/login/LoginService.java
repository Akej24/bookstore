package com.bookstoreapplication.bookstore.user.login;

import com.bookstoreapplication.bookstore.config.JwtService;
import com.bookstoreapplication.bookstore.user.account.User;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor
class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    String loginUser(@Valid LoginRequest request){
        User existingUser = userRepository.findByEmail(request.getEmail()).orElseThrow(()->{
            logger.warn("Unsuccessfully logged in - user with given email does not exist");
            throw new IllegalArgumentException("User with given email does not exist");
        });

        String passwordFromDatabase = existingUser.getPassword();
        String passwordFromRequest = request.getPassword();
        if(!bCryptPasswordEncoder.matches(passwordFromRequest, passwordFromDatabase)){
            logger.warn("Unsuccessfully logged in - invalid password for user with e-mail {}", request.getEmail());
            throw new IllegalArgumentException("Invalid password for user with given e-mail");
        }
        String jwtToken = jwtService.generateToken(existingUser);
        logger.info("User with e-mail {} successfully logged in - created jwt token", request.getEmail());
        return jwtToken;
    }
}
