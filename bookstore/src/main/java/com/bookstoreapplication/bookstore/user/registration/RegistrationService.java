package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import com.bookstoreapplication.bookstore.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private UserService userService;
    private RegistrationValidator registrationValidator;
    private static final String INVALID_PASSWORD_MSG = "The password must contain 8-16 characters, one lowercase letter, one uppercase letter, a special character and a number";

    public void registerUserIfPasswordValid(RegistrationRequest request) throws Exception {
        if(isPasswordValid(request)){
            registerUser(request);
        }else{
            throw new Exception(INVALID_PASSWORD_MSG);
        }
    }

    public void registerUser(RegistrationRequest request) {
        userService.signUpUser(
                new UserDatabaseModel(
                        request.getEmail(),
                        request.getSurname(),
                        request.getPassword(),
                        request.getName(),
                        request.getSurname(),
                        request.getDateOfBirth(),
                        request.getRole()
                )
        );
    }

    private boolean isPasswordValid(RegistrationRequest request) {
        return registrationValidator.validatePassword(request.getPassword());
    }

}
