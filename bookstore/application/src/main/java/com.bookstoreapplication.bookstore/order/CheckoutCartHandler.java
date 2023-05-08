package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.book.BookFacade;
import com.bookstoreapplication.bookstore.delivery.DeliveryFacade;
import com.bookstoreapplication.bookstore.order.exception.CheckoutCartNotFoundException;
import com.bookstoreapplication.bookstore.order.value_object.PaymentMethod;
import com.bookstoreapplication.bookstore.payment.PaymentFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
class CheckoutCartHandler {

    private final CartRepository cartRepository;
    private final CheckoutCartRepository checkoutCartRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;

    void updateDeliveryDetails(long customerId, DeliveryDetails deliveryDetails){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updateDeliveryDetails(deliveryDetails));
    }

    void updatePaymentMethod(long customerId, PaymentMethod paymentMethod){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updatePaymentMethod(paymentMethod));
    }

    void order(long customerId){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        pay(customerCheckoutCart);
        //...notify when available
        Order newOrder = customerCheckoutCart.placeOrder();
        List<OrderDetail> orderDetails = customerCheckoutCart.mapToOrderDetails(newOrder.getOrderId());

        BookFacade.updateBooksProductsAmount();
        DeliveryFacade.notifyDeliveryService();

        orderRepository.save(newOrder);
        orderDetailsRepository.saveAll(orderDetails);

        checkoutCartRepository.deleteAllByCustomerId(customerId);
        cartRepository.deleteAllByCustomerId(customerId);
    }

    private void pay(CheckoutCart customerCheckoutCart){
        if(customerCheckoutCart.hasEnoughDataToPayAndDeliver()) {
            PaymentFacade.pay(customerCheckoutCart.getPaymentMethod(), customerCheckoutCart.getCart().getTotalPrice());
        }
    }

    private CheckoutCart findCheckoutCartByCustomerId(long customerId){
        return checkoutCartRepository.findByCart_CustomerId(customerId).orElseThrow( () -> {
            log.warn("Checkout cart for customer with id {} does not exist", customerId);
            throw new CheckoutCartNotFoundException();
        });
    }


}
