package com.bookstoreapplication.bookstore.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class CheckoutCartHandler {

    private final CheckoutCartRepository checkoutCartRepository;
    private final OrderRepository orderRepository;

    void updateDelivery(long customerId, Delivery delivery){
        CheckoutCart customerCheckoutCart = checkoutCartRepository.findByCart_CustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updateDelivery(delivery));
    }

    void updatePayment(long customerId, Payment payment){
        CheckoutCart customerCheckoutCart = checkoutCartRepository.findByCart_CustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updatePayment(payment));
    }

    void order(long customerId){
        CheckoutCart customerCheckoutCart = checkoutCartRepository.findByCart_CustomerId(customerId);
        orderRepository.save(new Order(customerCheckoutCart));
    }


}
