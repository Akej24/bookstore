package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.exception.CheckoutCartNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
class CheckoutCartHandler {

    private final CheckoutCartRepository checkoutCartRepository;
    private final OrderRepository orderRepository;

    void updateDelivery(long customerId, DeliveryDetails deliveryDetails){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updateDelivery(deliveryDetails));
    }

    void updatePayment(long customerId, PaymentMethod paymentMethod){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updatePayment(paymentMethod));
    }

    void order(long customerId){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        orderRepository.save(new Order(customerCheckoutCart));
    }

    private CheckoutCart findCheckoutCartByCustomerId(long customerId){
        return checkoutCartRepository.findByCart_CustomerId(customerId).orElseThrow( () -> {
            log.warn("Checkout cart for customer with id {} does not exist", customerId);
            throw new CheckoutCartNotFoundException();
        });
    }


}
