package com.bookstoreapplication.bookstore.payment;

import com.bookstoreapplication.bookstore.payment.value_object.PaymentStatus;
import com.bookstoreapplication.bookstore.payment.value_object.ServiceType;
import com.bookstoreapplication.bookstore.purchase.value_object.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    @DisplayName("Should return true, when successfully changed payment method")
    void updateStatus() {
        Payment payment = getPaymentInitialState();
        payment.updateStatus(PaymentStatus.SUCCEED);
        assertEquals(payment.getPaymentStatus(), PaymentStatus.SUCCEED);
    }

    private static Payment getPaymentInitialState() {
        return new Payment(
            ServiceType.ORDER,
                UUID.fromString("1d1fc336-f0ac-4cd2-8747-79a7f97d701c"),
                PaymentMethod.BLIK,
                new TotalPrice(BigDecimal.valueOf(433.65))
        );
    }
}