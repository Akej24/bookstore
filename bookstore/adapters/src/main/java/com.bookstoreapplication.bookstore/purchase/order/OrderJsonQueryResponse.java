package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.purchase.value_object.OrderDate;
import com.bookstoreapplication.bookstore.purchase.value_object.OrderStatus;
import com.bookstoreapplication.bookstore.purchase.value_object.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class OrderJsonQueryResponse {

    private final UUID orderId;
    private final PaymentMethod paymentMethod;
    @JsonUnwrapped
    private final OrderDate orderDate;
    private final OrderStatus orderStatus;

    static List<OrderJsonQueryResponse> from(List<OrderQueryResponse> sources){
        return sources.stream().map(OrderJsonQueryResponse::from).collect(Collectors.toList());
    }

    static OrderJsonQueryResponse from(OrderQueryResponse source){
        return new OrderJsonQueryResponse(
                source.getOrderId(),
                source.getPaymentMethod(),
                source.getOrderDate(),
                source.getOrderStatus()
        );
    }
}
