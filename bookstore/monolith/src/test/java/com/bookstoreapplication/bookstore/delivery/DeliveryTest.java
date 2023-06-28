package com.bookstoreapplication.bookstore.delivery;

import com.bookstoreapplication.bookstore.delivery.exception.DeliveryAlreadyReceivedException;
import com.bookstoreapplication.bookstore.delivery.exception.DeliveryAlreadySendException;
import com.bookstoreapplication.bookstore.delivery.exception.NotSendDeliveryException;
import com.bookstoreapplication.bookstore.delivery.value_object.DeliveryStatus;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.Address;
import com.bookstoreapplication.bookstore.purchase.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    @DisplayName("Should return true when delivery was marked sent")
    void markSent_and_checkDeliveryStatusMatches() {
        Delivery deliveryToMarkSent = getDeliveryInitialState();
        Delivery sentDelivery = deliveryToMarkSent.markSent();
        assertEquals(sentDelivery.getDeliveryStatus(), DeliveryStatus.SEND);
    }

    @Test
    @DisplayName("Should return true when delivery was marked sent and next marked received")
    void markReceived_and_checkDeliveryStatusMatches() {
        Delivery deliveryToMarkSent = getDeliveryInitialState();
        Delivery receivedDelivery = deliveryToMarkSent.markSent().markReceived();
        assertEquals(receivedDelivery.getDeliveryStatus(), DeliveryStatus.RECEIVED);
    }

    @Test
    @DisplayName("Should throw DeliveryAlreadySendException when delivery was already marked as sent")
    void tryMarkSent_and_throwDeliveryAlreadySendException() {
        Delivery deliveryToMarkSent = getDeliveryInitialState();
        Exception exception = assertThrows(DeliveryAlreadySendException.class, () -> deliveryToMarkSent.markSent().markSent());
        assertTrue(exception.getMessage().contains("already sent"));
    }

    @Test
    @DisplayName("Should throw NotSendDeliveryException when delivery has not been send and try to receive it")
    void tryMarkReceived_and_throwNotSendDeliveryException() {
        Delivery deliveryToMarkReceived = getDeliveryInitialState();
        Exception exception = assertThrows(NotSendDeliveryException.class, deliveryToMarkReceived::markReceived);
        assertTrue(exception.getMessage().contains("cannot be marked as received"));
    }

    @Test
    @DisplayName("Should throw DeliveryAlreadyReceivedException when delivery has been already received")
    void tryMarkReceived_and_throwDeliveryAlreadyReceivedException() {
        Delivery deliveryToMarkReceived = getDeliveryInitialState();
        Exception exception = assertThrows(DeliveryAlreadyReceivedException.class, () -> deliveryToMarkReceived.markSent().markReceived().markReceived());
        assertTrue(exception.getMessage().contains("already received"));
    }

    private static Delivery getDeliveryInitialState() {
        return Delivery.from(
                new DeliveryCommand(UUID.fromString("1d1fc336-f0ac-4cd2-8747-79a7f97d701c"),
                        new Address(
                                new FirstName("TestFirstName"),
                                new LastName("TestLastName"),
                                new PhoneNumber("TestPhoneNumber"),
                                new Street("TestStreet"),
                                new StreetNumber(43),
                                new ZipCode("43-545"),
                                new City("TestCity")
                        ))
        );
    }
}