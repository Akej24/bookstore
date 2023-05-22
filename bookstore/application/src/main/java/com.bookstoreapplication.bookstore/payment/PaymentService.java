package com.bookstoreapplication.bookstore.payment;

import com.bookstoreapplication.bookstore.payment.value_object.PaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
class PaymentService {

    private final PaymentRepository paymentRepository;
    private final IPaymentDeserializer IPaymentDeserializer;

    @RabbitListener(queues = "payment")
    void receivePaymentFromQueue(String jsonPayment) throws InterruptedException {
        Payment newPayment = IPaymentDeserializer.fromJson(jsonPayment);

        //Payment transaction imitation
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Paying... [3sec]");
        newPayment.updateStatus(PaymentStatus.SUCCEED);
        if (newPayment.getTotalPrice().getTotalPrice().compareTo(BigDecimal.ZERO) < 0){
            newPayment.updateStatus(PaymentStatus.REJECTED);
        }
        //Payment transaction imitation
        paymentRepository.save(newPayment);
    }

}
