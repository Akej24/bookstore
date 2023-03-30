package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.query.SimpleUserQueryDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class UserService {

    private static final int PAGE_SIZE = 10;
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Cacheable(cacheNames = "User")
    public UserDto getUserById(long userId){
        User user = userRepository.findById(userId).orElseThrow( () -> {
            LOGGER.warn("User with id {} has not been found", userId);
            throw new IllegalArgumentException("User with given id does not exist");
        });
        LOGGER.info("User with id {} has been fetched from the database [Cached]", userId);
        return UserDtoMapper.mapToUserResponse(user);
    }

    @Cacheable(cacheNames = "Users")
    public List<UserDto> getAllUsers(int page) {
        List<User> users = userRepository.findAllUsers(PageRequest.of(page, PAGE_SIZE));
        LOGGER.info("All users have been fetched from the database [Cached]");
        return UserDtoMapper.mapToUsersResponse(users);
    }

    @Transactional
    void deleteUser(long userId) {
        User userToDelete = userRepository.findById(userId).orElseThrow(() -> {
            LOGGER.warn("The user with id {} does not exist", userId);
            throw new IllegalArgumentException("User with given id does not exist");
        });
        userRepository.delete(userToDelete);
        LOGGER.info("The book with id {} was successfully removed from the database", userId);
    }

    @Transactional
    void deleteAllUsers() {
        userRepository.deleteAll();
        LOGGER.warn("All users have been removed from the database");
    }

    @Transactional
    @CachePut(cacheNames = "User", key = "#root.target.savedUser.userId")
    public UserDto updateUserById(long userId, @Valid UserRequest userRequest) {
        User userToUpdate = userRepository.findById(userId).orElseThrow(() -> {
            LOGGER.warn("The user with id {} does not exist", userId);
            throw new IllegalArgumentException("User with given id does not exist");
        });
        User updatedUser = updateFromRequest(userToUpdate, userRequest);
        User savedUser = userRepository.save(updatedUser);
        LOGGER.info("The user with id {} has been updated [Cached]", userId);
        return UserDtoMapper.mapToUserResponse(savedUser);
    }

    public void updateUserFunds(Long userId, Double purchaseTotalPrice) {
        User user = findUserById(userId);
        BigDecimal userFunds = BigDecimal.valueOf(user.getAvailableFunds());
        BigDecimal totalPrice = BigDecimal.valueOf(purchaseTotalPrice);
        Double userNewFunds = userFunds.subtract(totalPrice).doubleValue();
        User userWithNewFunds = user.toBuilder().availableFunds(userNewFunds).build();
        userRepository.save(userWithNewFunds);
    }

    public void isUserAvailableToPay(Long userId, Double purchaseTotalPrice) {
        User user = findUserById(userId);
        if(user.getAvailableFunds()< purchaseTotalPrice){
            LOGGER.warn("User with id {} does not have enough funds to pay for purchase with id {}", user.getUserId(), purchaseTotalPrice);
            throw new IllegalArgumentException("User with given id does not have enough funds to pay for this purchase");
        }
    }

    public SimpleUserQueryDto getSimpleUserQueryDto(Long userId) {
        User user = findUserById(userId);
        return UserDtoMapper.mapToSimpleUserQueryDto(user);
    }

    public void existsUserById(Long userId) {
        findUserById(userId);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow( () -> {
            LOGGER.warn("User with id {} has not been found", userId);
            throw new IllegalArgumentException("User with given id does not exist");
        });
    }

    private User updateFromRequest(User userToUpdate, UserRequest userRequest) {
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
