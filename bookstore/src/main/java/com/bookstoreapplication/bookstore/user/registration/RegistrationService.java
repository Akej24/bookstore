package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import com.bookstoreapplication.bookstore.user.registration.exception.InvalidInputException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class RegistrationService {

    private final UserRepository userRepository;
    private final RegistrationValidator registrationValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String SUCCESSFULLY_REGISTERED = "Successfully registered - added to the database - session created";
    private static final String UNSUCCESSFULLY_REGISTERED = "Unsuccessfully registered - the password must contain 8-16 characters, one lowercase letter, one uppercase letter, a special character and a number, email must be not taken and fields must be not blank - session was not created";
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    public UserDatabaseModel registerUserIfPasswordValidAndEmailNotTaken(RegistrationRequest request) throws InvalidInputException {
        if(isPasswordValid(request) && isEmailNotTaken(request) && areFieldsNotBlank(request)){
            logger.info(SUCCESSFULLY_REGISTERED);
            return registerUserByRequestModel(request);
        }else{
            logger.warn(UNSUCCESSFULLY_REGISTERED);
            throw new InvalidInputException();
        }
    }

    private UserDatabaseModel registerUserByRequestModel(RegistrationRequest request) {
        var modelToSave = buildFromFormToDatabaseModel(request);
        return userRepository.save(modelToSave);
    }

    private boolean isEmailNotTaken(RegistrationRequest request) {
        return userRepository.findByEmail(request.getEmail()).isEmpty();
    }

    private boolean isPasswordValid(RegistrationRequest request) {
        return registrationValidator.validatePassword(request.getPassword());
    }

    private boolean areFieldsNotBlank(RegistrationRequest request){
        return registrationValidator.validateFields(request);
    }

    private UserDatabaseModel buildFromFormToDatabaseModel(RegistrationRequest request) {
        return UserDatabaseModel.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .surname(request.getSurname())
                .dateOfBirth(request.getDateOfBirth())
                .role(request.getRole())
                .locked(false)
                .enabled(true)
                .build();
    }

}
