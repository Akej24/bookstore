package com.bookstoreapplication.bookstore.delivery;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class DeliveryListener {

    private final AddressJsonConverter addressJsonConverter;
    private final DeliveryHandler deliveryHandler;

    @RabbitListener(queuesToDeclare = @Queue("${bookstore.rabbitmq.routing-keys.delivery}"))
    void receiveDeliveryFromQueue(String jsonDelivery) {
        DeliveryCommand deliveryCommand = addressJsonConverter.fromJson(jsonDelivery);
        deliveryHandler.prepareNewDelivery(deliveryCommand);
    }

}
