package com.bookstoreapplication.bookstore.delivery;

import com.bookstoreapplication.bookstore.delivery.value_object.DeliveryMethod;
import com.bookstoreapplication.bookstore.delivery.value_object.DeliveryStatus;
import com.bookstoreapplication.bookstore.delivery.value_object.SendDate;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.Address;
import com.bookstoreapplication.bookstore.purchase.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryQueryResponseTest {

    @Test
    @DisplayName("Should return true when first mapped delivery contains all correct field names and the second contains all correct values")
    void mapFromDeliveriesListToDeliveriesQueryResponseList() {
        var deliveriesToMap = getDeliveriesInitialStates();
        var mappedDeliveries = DeliveryQueryResponse.from(deliveriesToMap);
        assertThat(mappedDeliveries.get(0))
                .hasFieldOrProperty("orderId")
                .hasFieldOrProperty("deliveryNumber")
                .hasFieldOrProperty("deliveryMethod")
                .hasFieldOrProperty("address")
                .hasFieldOrProperty("deliveryStatus")
                .hasFieldOrProperty("sendDate")
                .hasFieldOrProperty("receiveDate");

        var secondDelivery = deliveriesToMap.get(1);
        var secondMappedDelivery = mappedDeliveries.get(1);
        assertEquals(secondDelivery.getOrderId(), secondMappedDelivery.getOrderId());
        assertEquals(secondDelivery.getDeliveryNumber(), secondMappedDelivery.getDeliveryNumber());
        assertEquals(secondDelivery.getDeliveryMethod(), secondMappedDelivery.getDeliveryMethod());
        assertEquals(secondDelivery.getDeliveryStatus(), secondMappedDelivery.getDeliveryStatus());
        assertEquals(secondDelivery.getSendDate(), secondMappedDelivery.getSendDate());
        assertEquals(secondDelivery.getReceiveDate(), secondMappedDelivery.getReceiveDate());
    }

    private static List<Delivery> getDeliveriesInitialStates() {
        return List.of(getFirstDeliveryInitialState(), getSecondDeliveryInitialState());
    }

    private static Delivery getFirstDeliveryInitialState() {
        return new Delivery(
                43,
                UUID.fromString("1d1fc336-f0ac-4cd2-8747-79a7f97d701c"),
                UUID.fromString("ddf68fcf-2b82-4ccc-9436-18efa6d8ae33"),
                DeliveryMethod.COURIER,
                new Address(
                        new FirstName("TestFirstNameTwo"),
                        new LastName("TestLastNameTwo"),
                        new PhoneNumber("TestPhoneNumberTwo"),
                        new Street("TestStreetTwo"),
                        new StreetNumber(76),
                        new ZipCode("96-575"),
                        new City("TestCityTwo")
                ),
                DeliveryStatus.RECEIVED,
                null,
                null
        );
    }

    private static Delivery getSecondDeliveryInitialState() {
        return new Delivery(
                73,
                UUID.fromString("702dd6be-7ee2-42ce-9144-d6b5602ab538"),
                UUID.fromString("5e99cc05-5839-481f-ba79-49540aa4991d"),
                DeliveryMethod.COURIER,
                        new Address(
                                new FirstName("TestFirstNameTwo"),
                                new LastName("TestLastNameTwo"),
                                new PhoneNumber("TestPhoneNumberTwo"),
                                new Street("TestStreetTwo"),
                                new StreetNumber(76),
                                new ZipCode("96-575"),
                                new City("TestCityTwo")
                        ),
                DeliveryStatus.RECEIVED,
                new SendDate(LocalDateTime.now()),
                null
        );
    }
}