package com.bookstoreapplication.bookstore.user.account;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor
class UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    User getUserById(long userId){
        var result = userRepository.findById(userId).orElseThrow( () -> {
            logger.warn("User with id {} has not been found", userId);
            throw new IllegalArgumentException("User with given id does not exist");
        });
        logger.info("User with id {} has been found", userId);
        return result;
    }

    List<User> getAllUsers() {
        var result = userRepository.findAll();
        logger.info("All users have been fetched from the database");
        return result;
    }

    @Transactional
    void deleteUser(long userId) {
        User userToDelete = userRepository.findById(userId).orElseThrow(() -> {
            logger.warn("The user with id {} does not exist", userId);
            throw new IllegalArgumentException("User with given id %s does not exist");
        });
        userRepository.delete(userToDelete);
        logger.info("The book with id {} was successfully removed from the database", userId);
    }

    @Transactional
    void deleteAllUsers() {
        userRepository.deleteAll();
        logger.warn("All users have been removed from the database");
    }

    @Transactional
    User updateUserById(long userId, @Valid UserRequest userRequest) {
        User userToUpdate = userRepository.findById(userId).orElseThrow(() -> {
            logger.warn("The user with id {} does not exist", userId);
            throw new IllegalArgumentException("User with given id does not exist");
        });
        var updatedUser = updateFromRequest(userToUpdate, userRequest);
        var savedUser = userRepository.save(updatedUser);
        logger.info("The user with id {} has been updated", userId);
        return savedUser;
    }

    private static User updateFromRequest(User userToUpdate, UserRequest userRequest) {
        return userToUpdate.toBuilder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .dateOfBirth(userRequest.getDateOfBirth())
                .role(userRequest.getRole())
                .build();
    }

}
