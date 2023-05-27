package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.exception.EmailTakenException;
import com.bookstoreapplication.bookstore.user.exception.UserDoesNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@AllArgsConstructor
class UserHandler {

    private final UserRepository userRepository;
    private final IPasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(@Valid UserCommand source) {
        if(userRepository.findByEmail(source.email()).isPresent()){
            log.warn("Unsuccessfully registered - email must be not taken");
            throw new EmailTakenException();
        }
        String encodedPassword = passwordEncoder.encode(source.password().getPassword());
        userRepository.save(new User(source, encodedPassword));
        log.info("Successfully registered");
    }

    public UserQueryResponse getUserById(long userId){
        User user = findUserById(userId);
        log.info("User with id {} has been fetched from the database", userId);
        return UserQueryResponse.from(user);
    }

    public List<UserQueryResponse> getAllUsers(PageRequest pageRequest) {
        List<User> users = userRepository.findAllBy(pageRequest);
        log.info("All users have been fetched from the database");
        return UserQueryResponse.from(users);
    }

    @Transactional
    public void deleteUser(long userId) {
        User userToDelete = findUserById(userId);
        userRepository.delete(userToDelete);
        log.info("The book with id {} was successfully removed from the database", userId);
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
        log.warn("All users have been removed from the database");
    }

    @Transactional
    public UserQueryResponse updateUserById(long userId, @Valid UserUpdateCommand source) {
        User userToUpdate = findUserById(userId);
        String encodedPassword = passwordEncoder.encode(source.password().getPassword());
        User savedUser = userRepository.save(userToUpdate.update(source, encodedPassword));
        log.info("The user with id {} has been updated", userId);
        return UserQueryResponse.from(savedUser);
    }

    public User findUserById(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow( () -> {
            log.warn("User with id {} has not been found", userId);
            throw new UserDoesNotExistException();
        });
    }

    int countAllUsers(){
        List<User> users = userRepository.findAll();
        log.info("All users have been fetched from the database");
        return users.size();
    }

}
