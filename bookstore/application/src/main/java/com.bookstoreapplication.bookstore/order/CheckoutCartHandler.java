package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.book.BookFacade;
import com.bookstoreapplication.bookstore.delivery.DeliveryFacade;
import com.bookstoreapplication.bookstore.order.exception.CheckoutCartNotFoundException;
import com.bookstoreapplication.bookstore.order.exception.NotEnoughDataToPayAndDeliverException;
import com.bookstoreapplication.bookstore.order.value_object.PaymentMethod;
import com.bookstoreapplication.bookstore.payment.PaymentFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
class CheckoutCartHandler {

    private final CartHandler cartHandler;
    private final CartRepository cartRepository;
    private final CheckoutCartRepository checkoutCartRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;
    private final PlacedOrderPublisher placedOrderPublisher;

    void updateAddress(long customerId, Address address){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updateAddress(address));
    }

    void updatePaymentMethod(long customerId, PaymentMethod paymentMethod){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updatePaymentMethod(paymentMethod));
    }

    void order(long customerId){
        Cart customerCart = cartHandler.findCartByCustomerId(customerId);
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);

        if(!customerCheckoutCart.hasEnoughDataToPayAndDeliver()) {
            throw new NotEnoughDataToPayAndDeliverException();
        }
        PaymentFacade.pay(customerCheckoutCart.getPaymentMethod(), customerCart.getTotalPrice());

        Order newOrder = customerCheckoutCart.placeOrder();
        orderRepository.save(newOrder);

        List<OrderDetail> orderDetails = customerCart.mapToOrderDetails(newOrder.getOrderId());
        orderDetailsRepository.saveAll(orderDetails);

        BookFacade.updateBooksProductsAmount();
        DeliveryFacade.notifyDeliveryService();

        checkoutCartRepository.deleteAllByCustomerId(customerId);
        cartRepository.deleteAllByCustomerId(customerId);

        placedOrderPublisher.publishPlacedOrderEvent(customerCart, customerCheckoutCart, newOrder);
    }

    private CheckoutCart findCheckoutCartByCustomerId(long customerId){
        return checkoutCartRepository.findByCart_CustomerId(customerId).orElseThrow( () -> {
            log.warn("Checkout cart for customer with id {} does not exist", customerId);
            throw new CheckoutCartNotFoundException();
        });
    }


}
