package com.bookstoreapplication.bookstore.payment;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@AllArgsConstructor
class PaymentService {

    private final PaymentRepository paymentRepository;
    private final IPaymentDeserializer IPaymentDeserializer;

    @RabbitListener(queues = "payment")
    void receivePaymentFromQueue(String jsonPayment) {
        System.out.println(jsonPayment);
        Payment newPayment = IPaymentDeserializer.fromJson(jsonPayment);
        paymentRepository.save(newPayment);
    }

}
