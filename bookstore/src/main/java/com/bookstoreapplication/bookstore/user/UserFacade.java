package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.query.SimpleUserQueryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserFacade {

    private final UserService userService;

    public void updateUserFunds(Long userId, Double purchaseTotalPrice) {
        userService.updateUserFunds(userId, purchaseTotalPrice);
    }

    public void isUserAvailableToPay(Long userId, Double purchaseTotalPrice) {
        userService.isUserAvailableToPay(userId, purchaseTotalPrice);
    }

    public void existsUserById(Long userId) {
        userService.existsUserById(userId);
    }

    public SimpleUserQueryDto getSimpleUserQueryDto(Long userId){
        return userService.getSimpleUserQueryDto(userId);
    }
}
