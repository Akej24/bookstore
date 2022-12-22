package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import com.bookstoreapplication.bookstore.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private UserService userService;
    private Validator validator;
    public String register(RegistrationRequest request) throws Exception {
        if(validator.validatePassword(request.password())){
            return userService.signUpUser(
                    new UserDatabaseModel(
                            request.email(),
                            request.username(),
                            request.password(),
                            request.name(),
                            request.surname(),
                            request.dateOfBirth(),
                            request.role()
                    )
            );
        }else{
            throw new Exception("Hasło musi zawierać od 8-16 znaków, jedną małą literę, jedną wielką literę, znak specjalny oraz cyfrę");
        }

    }

}
