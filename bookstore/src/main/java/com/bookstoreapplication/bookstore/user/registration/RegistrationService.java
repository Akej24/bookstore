package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import com.bookstoreapplication.bookstore.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private UserService userService;
    public String register(RegistrationRequest request){
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
    }
}
