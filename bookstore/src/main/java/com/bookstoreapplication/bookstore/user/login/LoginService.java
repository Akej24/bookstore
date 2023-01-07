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

    public boolean startUserSession(LoginRequest request){
        if(userExistsByEmail(request) && userPasswordMatchesEmail(request)){
            System.out.println("Session started...");
            return true;
        }else{
            return false;
        }
    }

    private boolean userPasswordMatchesEmail(LoginRequest request) {
       var passwordFromDatabase = userRepository.findByEmail(request.getEmail()).orElseThrow().getPassword();
       var passwordFromForm = request.getPassword();
       return bCryptPasswordEncoder.matches(passwordFromForm, passwordFromDatabase);
    }

    private boolean userExistsByEmail(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail()).isPresent();
    }

}
