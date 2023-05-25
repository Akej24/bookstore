package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.purchase.value_object.OrderDate;
import com.bookstoreapplication.bookstore.purchase.value_object.OrderStatus;
import com.bookstoreapplication.bookstore.purchase.value_object.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
class OrderQueryResponse {

    private UUID orderId;
    private PaymentMethod paymentMethod;
    @JsonUnwrapped
    private OrderDate orderDate;
    private OrderStatus orderStatus;

    static Set<OrderQueryResponse> toResponses(List<Order> sources){
        return sources.stream().map(OrderQueryResponse::toResponse).collect(Collectors.toSet());
    }

    static OrderQueryResponse toResponse(Order source){
        return new OrderQueryResponse(
                source.getOrderId(),
                source.getPaymentMethod(),
                source.getOrderDate(),
                source.getOrderStatus()
        );
    }
}
