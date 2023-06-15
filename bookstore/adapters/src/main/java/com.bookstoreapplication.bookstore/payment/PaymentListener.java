package com.bookstoreapplication.bookstore.payment;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class PaymentListener {

    private final PaymentJsonConverter paymentJsonConverter;
    private final PaymentHandler paymentHandler;

    @RabbitListener(queuesToDeclare = @Queue("${bookstore.rabbitmq.routing-keys.payment}"))
    void receivePaymentFromQueue(String jsonPayment) throws InterruptedException {
        Payment newPayment = paymentJsonConverter.fromJson(jsonPayment);
        paymentHandler.registerNewPayment(newPayment);
    }

}
