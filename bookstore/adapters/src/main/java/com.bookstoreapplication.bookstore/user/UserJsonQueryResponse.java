package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.purchase.value_object.Funds;
import com.bookstoreapplication.bookstore.user.value_objects.*;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class UserJsonQueryResponse {

    private final long userId;
    @JsonUnwrapped
    private final UserEmail email;
    @JsonUnwrapped
    private final Username username;
    @JsonUnwrapped
    private final FirstName firstName;
    @JsonUnwrapped
    private final LastName lastName;
    @JsonUnwrapped
    private final DateOfBirth dateOfBirth;
    @JsonUnwrapped
    private final Funds funds;

    static List<UserJsonQueryResponse> from(List<UserQueryResponse> sources){
        return sources.stream().map(UserJsonQueryResponse::from).collect(Collectors.toList());
    }

    static UserJsonQueryResponse from(UserQueryResponse source){
        return new UserJsonQueryResponse(
                source.getUserId(),
                source.getEmail(),
                source.getUsername(),
                source.getFirstName(),
                source.getLastName(),
                source.getDateOfBirth(),
                source.getFunds());
    }
}
