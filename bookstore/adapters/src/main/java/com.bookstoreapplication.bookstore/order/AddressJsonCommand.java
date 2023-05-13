package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import com.fasterxml.jackson.annotation.JsonCreator;
import dev.mccue.json.Json;
import dev.mccue.json.JsonDecoder;
import dev.mccue.json.JsonEncodable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class AddressJsonCommand implements JsonEncodable {

    private FirstName firstName;
    private LastName lastName;
    private PhoneNumber phoneNumber;
    private Street street;
    private StreetNumber streetNumber;
    private ZipCode zipCode;
    private City city;

    @JsonCreator
    public static Address fromJson(Json json) {
        return new Address(
                JsonDecoder.field(json, "firstName", firstName ->
                        new FirstName(JsonDecoder.string(firstName))),

                JsonDecoder.field(json, "lastName", lastName ->
                        new LastName(JsonDecoder.string(lastName))),

                JsonDecoder.field(json, "phoneNumber", phoneNumber ->
                        new PhoneNumber(JsonDecoder.string(phoneNumber))),

                JsonDecoder.field(json, "street", street ->
                        new Street(JsonDecoder.string(street))),

                JsonDecoder.field(json, "streetNumber", streetNumber ->
                        new StreetNumber(JsonDecoder.int_(streetNumber))),

                JsonDecoder.field(json, "zipCode", zipCode ->
                        new ZipCode(JsonDecoder.string(zipCode))),

                JsonDecoder.field(json, "city", city ->
                        new City(JsonDecoder.string(city)))
        );
    }

    @Override
    public Json toJson() {
        return Json.objectBuilder()
                .put("firstName", Json.of(firstName.getFirstName()))
                .put("lastName", Json.of(lastName.getLastName()))
                .put("phoneNumber", Json.of(phoneNumber.getPhoneNumber()))
                .put("street", Json.of(street.getStreet()))
                .put("streetNumber", Json.of(streetNumber.getStreetNumber()))
                .put("zipCode", Json.of(zipCode.getZipCode()))
                .put("city", Json.of(city.getCity()))
                .build();
    }

}
