package com.bookstoreapplication.bookstore.order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
class OrderHandler {

    private final OrderRepository orderRepository;

    @Transactional
    public Set<OrderQueryResponse> getCustomerOrders(long customerId){
        List<Order> customerOrders = orderRepository.findAllByCustomerId(customerId);
        log.info("All orders for user with id {} has been fetched from database", customerId);
        return OrderQueryResponse.toResponses(customerOrders);
    }

}
