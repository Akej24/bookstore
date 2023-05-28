package com.bookstoreapplication.bookstore.delivery;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Slf4j
@Validated
@Service
@AllArgsConstructor
class DeliveryHandler {

    private final DeliveryRepository deliveryRepository;

    void prepareNewDelivery(@Valid DeliveryCommand deliveryCommand) {


    }
}
