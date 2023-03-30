package com.bookstoreapplication.bookstore.user;

import java.util.List;
import java.util.stream.Collectors;

class UserDtoMapper {

    private UserDtoMapper() {
    }

    public static SimpleUserQueryDto mapToSimpleUserQueryDto(User user) {
        return SimpleUserQueryDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .availableFunds(user.getAvailableFunds())
                .build();
    }

    static List<UserDto> mapToUsersResponse(List<User> users){
        return users.stream()
                .map(UserDtoMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    static UserDto mapToUserResponse(User user){
        return UserDto.builder()
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
