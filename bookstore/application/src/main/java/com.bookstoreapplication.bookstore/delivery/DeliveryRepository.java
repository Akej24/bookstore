package com.bookstoreapplication.bookstore.delivery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface DeliveryRepository {

    <S extends Delivery> S save(S entity);

    List<Delivery> findAllBy();

    Optional<Delivery> findByOrderId(UUID orderId);

    Optional<Delivery> findByDeliveryNumber(UUID deliveryNumber);

}
