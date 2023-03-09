package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.User;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
@AllArgsConstructor
class RegistrationService {

    private final UserRepository userRepository;
    private final RegistrationValidator registrationValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    @Transactional
    void registerUserIfPasswordValidAndEmailNotTaken(@Valid RegistrationRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            logger.warn("Unsuccessfully registered - email must be not taken");
            throw new IllegalArgumentException("Unsuccessfully registered - email must be not taken");
        }
        if(!registrationValidator.validatePassword(request.getPassword())){
            logger.warn("Unsuccessfully registered - the password must contain 8-16 characters, one lowercase letter, one uppercase letter, a special character and a number");
            throw new IllegalArgumentException("Unsuccessfully registered - the password must contain 8-16 characters, one lowercase letter, one uppercase letter, a special character and a number");
        }
        User modelToSave = createUserFromRequest(request);
        userRepository.save(modelToSave);
        logger.info("Successfully registered with email {}", request.getEmail());
    }

    private User createUserFromRequest(RegistrationRequest request) {
        return User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .surname(request.getSurname())
                .dateOfBirth(request.getDateOfBirth())
                .role(request.getRole())
                .build();
    }

}
