package com.bookstoreapplication.bookstore.user.login;

import com.bookstoreapplication.bookstore.user.account.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void startUserSession(LoginRequest request){
        if(userExistsByEmail(request) && userPasswordMatchesEmail(request)){
            System.out.println("Session started...");
        }
    }

    private boolean userPasswordMatchesEmail(LoginRequest request) {
       var passwordFromDatabase = userRepository.findByEmail(request.getEmail()).get().getPassword();
       var passwordFromForm = request.getPassword();
       return bCryptPasswordEncoder.matches(passwordFromDatabase, passwordFromForm);
    }

    private boolean userExistsByEmail(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail()).isPresent();
    }

}
