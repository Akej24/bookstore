package com.bookstoreapplication.bookstore.payment;

import com.bookstoreapplication.bookstore.payment.value_object.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;

@Slf4j
@Validated
@AllArgsConstructor
class PaymentHandler {

    private final PaymentRepository paymentRepository;

    void registerNewPayment(@Valid Payment newPayment) throws InterruptedException {
        // Payment transaction imitation
        Thread.sleep(3000);
        System.out.println("Paying... [3sec]");
        newPayment.updateStatus(PaymentStatus.SUCCEED);
        if (newPayment.getTotalPrice().getTotalPrice().compareTo(BigDecimal.ZERO) < 0){
            newPayment.updateStatus(PaymentStatus.REJECTED);
            log.warn("Unsuccessfully paid");
        }
        paymentRepository.save(newPayment);
        log.info("Successfully paid");
        // Payment transaction imitation
    }
}
