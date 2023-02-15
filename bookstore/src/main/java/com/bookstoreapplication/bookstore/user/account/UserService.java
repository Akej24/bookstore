package com.bookstoreapplication.bookstore.user.account;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDatabaseModel getUserById(long userId){
        if(userRepository.findById(userId).isPresent()){
            var result = userRepository.findById(userId).get();
            logger.info("User with the given id has been found");
            return result;
        }else{
            logger.warn("User with the given id has not been found");
            throw new IllegalArgumentException("User with given id does not exist");
        }
    }

    public List<UserDatabaseModel> getAllUsers() {
        var result = userRepository.findAll();
        logger.info("All users have been fetched from the database");
        return result;
    }

    public void deleteUser(long bookId) {
        if(userRepository.existsById(bookId)){
            userRepository.deleteById(bookId);
            logger.info("The user was successfully removed from the database");
        }else{
            logger.warn("User with the given id has not been found");
            throw new IllegalArgumentException("User with given id does not exist");
        }
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
        logger.warn("All users have been removed from the database");
    }

    public UserDatabaseModel updateUserById(long userId, UserUpdateRequest userUpdateRequest) {
        if(userRepository.findById(userId).isPresent()){
            var userToEdit = buildFromWriteToDatabaseModelWithId(userId, userUpdateRequest);
            return userRepository.save(userToEdit);
        }else{
            logger.warn("User with the given id has not been found");
            throw new IllegalArgumentException("User with given id does not exist");
        }
    }

    private static UserDatabaseModel buildFromWriteToDatabaseModelWithId(long bookId, UserUpdateRequest userUpdateRequest) {
        return UserDatabaseModel.builder()
                .userId(bookId)
                .email(userUpdateRequest.getEmail())
                .username(userUpdateRequest.getUsername())
                .password(userUpdateRequest.getPassword())
                .name(userUpdateRequest.getName())
                .surname(userUpdateRequest.getSurname())
                .dateOfBirth(userUpdateRequest.getDateOfBirth())
                .build();
    }

}
