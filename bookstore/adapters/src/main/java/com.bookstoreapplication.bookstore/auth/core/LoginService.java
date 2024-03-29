package com.bookstoreapplication.bookstore.auth.core;

import com.bookstoreapplication.bookstore.auth.exception.UserEmailHasNotBeenFoundException;
import com.bookstoreapplication.bookstore.user.value_objects.Password;
import com.bookstoreapplication.bookstore.user.value_objects.UserEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Slf4j
@Validated
@AllArgsConstructor
public class LoginService {

    private final SecuredUserRepository securedUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtManager jwtManager;

    public String verifyUserPasswordMatches(@Valid UserEmail email, Password password){
        SecuredUser user = getSecuredUser(email);
        user.checkPasswordsMatch(password, bCryptPasswordEncoder);
        return String.valueOf(user.getUserId());
    }

    public String generateJwt(@Valid UserEmail email) {
        SecuredUser user = getSecuredUser(email);
        return jwtManager.generateJwt(user);
    }

    private SecuredUser getSecuredUser(UserEmail email) {
        return securedUserRepository.findByUserEmailEmail(email.getEmail()).orElseThrow( () -> {
            log.warn("User with given e-mail has not been found");
            throw new UserEmailHasNotBeenFoundException();
        });
    }
}
