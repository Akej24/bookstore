package com.bookstoreapplication.bookstore.delivery;

import com.bookstoreapplication.bookstore.delivery.exception.DeliveryNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@AllArgsConstructor
class DeliveryHandler {

    private final DeliveryRepository deliveryRepository;

    @Transactional
    void prepareNewDelivery(@Valid DeliveryCommand deliveryCommand) {
        Delivery newDelivery = Delivery.from(deliveryCommand);
        deliveryRepository.save(newDelivery);
        log.info("New delivery registered with number {}", newDelivery.getDeliveryNumber());
    }

    DeliveryQueryResponse getDeliveryByOrderNumber(UUID orderId) {
        Delivery delivery = findDeliveryByOrderId(orderId);
        log.info("Successively fetch a delivery with order number {} from the database", orderId);
        return DeliveryQueryResponse.from(delivery);
    }

    List<DeliveryQueryResponse> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAllBy();
        log.info("All deliveries have been fetched from database");
        return DeliveryQueryResponse.from(deliveries);
    }

    @Transactional
    void markDeliveryAsSent(UUID deliveryNumber) {
        Delivery deliveryToMark = findDeliveryById(deliveryNumber);
        Delivery sentDelivery = deliveryToMark.markSent();
        deliveryRepository.save(sentDelivery);
        log.info("Successfully marked delivery with number {} as sent and saved to database",
                sentDelivery.getDeliveryNumber());
    }

    @Transactional
    void markDeliveryAsReceived(UUID deliveryNumber) {
        Delivery deliveryToMark = findDeliveryById(deliveryNumber);
        Delivery receivedDelivery = deliveryToMark.markReceived();
        deliveryRepository.save(receivedDelivery);
        log.info("Successfully marked delivery with number {} as received and saved to database",
                receivedDelivery.getDeliveryNumber());
    }

    private Delivery findDeliveryByOrderId(UUID orderId){
        return deliveryRepository.findByOrderId(orderId).orElseThrow( () -> {
            log.warn("Delivery with order id {} has not been found", orderId);
            throw new DeliveryNotFoundException();
        });
    }

    private Delivery findDeliveryById(UUID deliveryNumber) {
        return deliveryRepository.findByDeliveryNumber(deliveryNumber).orElseThrow( () -> {
            log.warn("Delivery with id {} has not been found", deliveryNumber);
            throw new DeliveryNotFoundException();
        });
    }
}
