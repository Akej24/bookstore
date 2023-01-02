package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class RegistrationService {

    private final UserRepository userRepository;
    private final RegistrationValidator registrationValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean registerUserIfPasswordValidAndEmailNotTaken(RegistrationRequest request){
        if(isPasswordValid(request) && isEmailNotTaken(request)){
            registerUserByRequestModel(request);
            return true;
        }else{
            return false;
        }
    }

    private void registerUserByRequestModel(RegistrationRequest request) {
        var modelToSave = new UserDatabaseModel(
                request.getEmail(),
                request.getUsername(),
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
