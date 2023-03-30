package com.bookstoreapplication.bookstore.user;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class UserFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);
    private final UserService userService;
    private final UserRepository userRepository;

    public void updateUserFunds(Long userId, Double purchaseTotalPrice) {
        User user = userService.findUserById(userId);
        BigDecimal userFunds = BigDecimal.valueOf(user.getAvailableFunds());
        BigDecimal totalPrice = BigDecimal.valueOf(purchaseTotalPrice);
        Double userNewFunds = userFunds.subtract(totalPrice).doubleValue();
        User userWithNewFunds = user.toBuilder().availableFunds(userNewFunds).build();
        userRepository.save(userWithNewFunds);
    }

    public void isUserAvailableToPay(Long userId, Double purchaseTotalPrice) {
        User user = userService.findUserById(userId);
        if(user.getAvailableFunds()< purchaseTotalPrice){
            LOGGER.warn("User with id {} does not have enough funds to pay for purchase with id {}", user.getUserId(), purchaseTotalPrice);
            throw new IllegalArgumentException("User with given id does not have enough funds to pay for this purchase");
        }
    }

    public SimpleUserQueryDto getSimpleUserQueryDto(Long userId) {
        User user = userService.findUserById(userId);
        return UserDtoMapper.mapToSimpleUserQueryDto(user);
    }

    public void existsUserById(Long userId) {
        userService.findUserById(userId);
    }

    public SimpleUserQueryDto findByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserDtoMapper.mapToSimpleUserQueryDto(user);
    }
}
