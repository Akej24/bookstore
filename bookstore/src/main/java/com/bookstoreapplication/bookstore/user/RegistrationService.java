package com.bookstoreapplication.bookstore.user;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    @Transactional
    void registerUserIfPasswordValidAndEmailNotTaken(@Valid RegistrationRequest request) {
        validateEmail(request);
        validatePassword(request);
        User modelToSave = createUserFromRequest(request);
        userRepository.save(modelToSave);
        LOGGER.info("Successfully registered with email {}", request.getEmail());
    }

    private void validatePassword(RegistrationRequest request) {
        if(!registrationValidator.validatePassword(request.getPassword())){
            LOGGER.warn("Unsuccessfully registered - the password must contain 8-16 characters, one lowercase letter, one uppercase letter, a special character and a number");
            throw new IllegalArgumentException("Unsuccessfully registered - the password must contain 8-16 characters, one lowercase letter, one uppercase letter, a special character and a number");
        }
    }

    private void validateEmail(RegistrationRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            LOGGER.warn("Unsuccessfully registered - email must be not taken");
            throw new IllegalArgumentException("Unsuccessfully registered - email must be not taken");
        }
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
