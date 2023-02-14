package com.bookstoreapplication.bookstore.user.login;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public UserDatabaseModel loginUser(LoginRequest request){
        if(userExistsByEmail(request) && userPasswordMatchesEmail(request)){
            logger.info("Successfully logged in");
            return userRepository.findByEmail(request.getEmail()).get();
        }else{
            logger.warn("Unsuccessfully logged in");
            throw new IllegalArgumentException("User with given email or password does not exist");
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
