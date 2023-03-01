package com.bookstoreapplication.bookstore.user.login;

import com.bookstoreapplication.bookstore.user.account.User;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public User loginUser(LoginRequest request){
        User existingUser = userRepository.findByEmail(request.getEmail()).orElseThrow(()->{
            logger.warn("Unsuccessfully logged in - user with given email does not exist");
            throw new IllegalArgumentException("User with given email does not exist");
        });

        var passwordFromDatabase = existingUser.getPassword();
        var passwordFromRequest = request.getPassword();
        if(!bCryptPasswordEncoder.matches(passwordFromRequest, passwordFromDatabase)){
            logger.warn("Unsuccessfully logged in - invalid password for user with e-mail {}", request.getEmail());
            throw new IllegalArgumentException("Invalid password for user with given e-mail");
        }

        logger.info("User with e-mail {} successfully logged in", request.getEmail());
        return existingUser;
    }
}
