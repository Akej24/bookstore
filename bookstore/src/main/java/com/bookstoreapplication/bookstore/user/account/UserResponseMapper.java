package com.bookstoreapplication.bookstore.user.account;

import java.util.List;
import java.util.stream.Collectors;

class UserResponseMapper {

    private UserResponseMapper() {
    }

    static List<UserResponse> mapToUsersResponse(List<User> users){
        return users.stream()
                .map(UserResponseMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    static UserResponse mapToUserResponse(User user){
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .dateOfBirth(user.getDateOfBirth())
                .role(user.getRole())
                .availableFunds(user.getAvailableFunds())
                .build();
    }
}
