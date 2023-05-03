package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.exception.EmailTakenException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
class UserHandler {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void registerUser(@Valid UserCommand source) {
        if(userRepository.findByEmail(source.getEmail()).isPresent()){
            log.warn("Unsuccessfully registered - email must be not taken");
            throw new EmailTakenException();
        }
        String encodedPassword = bCryptPasswordEncoder.encode(source.getPassword().getPassword());
        userRepository.save(new User(source, encodedPassword));
        log.info("Successfully registered");
    }

    @Cacheable(cacheNames = "User")
    public UserQueryResponse getUserById(long userId){
        User user = findUserById(userId);
        log.info("User with id {} has been fetched from the database", userId);
        return UserQueryResponse.toResponse(user);
    }

    @Cacheable(cacheNames = "Users")
    public Set<UserQueryResponse> getAllUsers(PageRequest pageRequest) {
        List<User> users = userRepository.findAllBy(pageRequest);
        log.info("All users have been fetched from the database");
        return UserQueryResponse.toResponses(users);
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
    @CachePut(cacheNames = "User")
    public UserQueryResponse updateUserById(long userId, @Valid UserUpdateCommand source) {
        User userToUpdate = findUserById(userId);
        String encodedPassword = bCryptPasswordEncoder.encode(source.getPassword().getPassword());
        User savedUser = userRepository.save(userToUpdate.update(source, encodedPassword));
        log.info("The user with id {} has been updated [Cached]", userId);
        return UserQueryResponse.toResponse(savedUser);
    }

    public User findUserById(Long userId) {
        return userRepository.findByUserId_UserId(userId).orElseThrow( () -> {
            log.warn("User with id {} has not been found", userId);
            throw new IllegalArgumentException("User with given id does not exist");
        });
    }

}
