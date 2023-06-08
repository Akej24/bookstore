package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.value_objects.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import dev.mccue.json.Json;
import dev.mccue.json.JsonDecoder;
import dev.mccue.json.JsonEncodable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class UserJsonCommand implements JsonEncodable {

    private final UserEmail email;
    private final Username username;
    private final Password password;
    private final FirstName firstName;
    private final LastName lastName;
    private final DateOfBirth dateOfBirth;
    private final UserRole role;

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @JsonCreator
    public static UserCommand fromJson(Json json) {
        return new UserCommand(
                JsonDecoder.field(json, "email", email ->
                        new UserEmail(JsonDecoder.string(email))),

                JsonDecoder.field(json, "username", username ->
                        new Username(JsonDecoder.string(username))),

                JsonDecoder.field(json, "password", password ->
                        new Password(JsonDecoder.string(password))),

                JsonDecoder.field(json, "firstName", firstName ->
                        new FirstName(JsonDecoder.string(firstName))),

                JsonDecoder.field(json, "lastName", lastName ->
                        new LastName(JsonDecoder.string(lastName))),

                JsonDecoder.field(json, "dateOfBirth", dateOfBirth -> {
                    LocalDate date;
                    try {
                        date = LocalDate.parse(JsonDecoder.string(dateOfBirth), LOCAL_DATE_FORMATTER);
                    } catch(DateTimeParseException dateTimeParseException) {
                        throw new IllegalArgumentException("Invalid date of birth");
                    }
                    return new DateOfBirth(date);
                }),

                JsonDecoder.field(json, "role", role -> UserRole.valueOf(JsonDecoder.string(role)))
        );
    }

    @Override
    public Json toJson() {
        return Json.objectBuilder()
                .put("email", Json.of(email.getEmail()))
                .put("username", Json.of(username.getUsername()))
                .put("password", Json.of(password.getPassword()))
                .put("firstName", Json.of(firstName.getFirstName()))
                .put("lastName", Json.of(lastName.getLastName()))
                .put("dateOfBirth", Json.of(LOCAL_DATE_FORMATTER.format(dateOfBirth.getDateOfBirth())))
                .put("role", Json.of(role.name()))
                .build();
    }
}
