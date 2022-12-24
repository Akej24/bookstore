package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RegistrationValidator registrationValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String INVALID_PASSWORD_OR_EMAIL_MSG = "The password must contain 8-16 characters, one lowercase letter, one uppercase letter, a special character and a number and email must be not taken";


    public void registerUserIfPasswordValidAndEmailNotTaken(RegistrationRequest request) throws Exception {
        if(isPasswordValid(request) && isEmailNotTaken(request)){
            registerUserByRequestModel(request);
        }else{
            throw new Exception(INVALID_PASSWORD_OR_EMAIL_MSG);
        }
    }

    public void registerUserByRequestModel(RegistrationRequest request) {
        var modelToSave = new UserDatabaseModel(
                request.getEmail(),
                request.getSurname(),
                bCryptPasswordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getSurname(),
                request.getDateOfBirth(),
                request.getRole()
        );
        userRepository.save(modelToSave);
    }

    private boolean isEmailNotTaken(RegistrationRequest request) {
        return userRepository.findByEmail(request.getEmail()).isEmpty();
    }

    private boolean isPasswordValid(RegistrationRequest request) {
        return registrationValidator.validatePassword(request.getPassword());
    }

}
