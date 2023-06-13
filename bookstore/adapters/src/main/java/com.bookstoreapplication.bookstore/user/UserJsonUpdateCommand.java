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

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class   UserJsonUpdateCommand implements JsonEncodable {

    private final Username username;
    private final Password password;
    private final FirstName firstName;
    private final LastName lastName;
    private final DateOfBirth dateOfBirth;

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @JsonCreator
    public static UserUpdateCommand fromJson(Json json) {
        return new UserUpdateCommand(

                JsonDecoder.field(json, "username", username ->
                        new Username(JsonDecoder.string(username))),

                JsonDecoder.field(json, "password", password ->
                        new Password(JsonDecoder.string(password))),

                JsonDecoder.field(json, "firstName", firstName ->
                        new FirstName(JsonDecoder.string(firstName))),

                JsonDecoder.field(json, "lastName", lastName ->
                        new LastName(JsonDecoder.string(lastName))),

                JsonDecoder.field(json, "dateOfBirth", dateOfBirth ->
                        new DateOfBirth(LocalDate.parse(JsonDecoder.string(dateOfBirth), LOCAL_DATE_FORMATTER)))

        );
    }

    @Override
    public Json toJson() {
        return Json.objectBuilder()
                .put("username", Json.of(username.getUsername()))
                .put("password", Json.of(password.getPassword()))
                .put("firstName", Json.of(firstName.getFirstName()))
                .put("lastName", Json.of(lastName.getLastName()))
                .put("dateOfBirth", Json.of(LOCAL_DATE_FORMATTER.format(dateOfBirth.getDateOfBirth())))
                .build();
    }
}
