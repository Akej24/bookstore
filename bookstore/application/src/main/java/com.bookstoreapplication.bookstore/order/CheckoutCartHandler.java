package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.exception.CheckoutCartNotFoundException;
import com.bookstoreapplication.bookstore.order.exception.NotEnoughDataToPayAndDeliverException;
import com.bookstoreapplication.bookstore.order.value_object.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@AllArgsConstructor
class CheckoutCartHandler {

    private final OrderMQTemplate orderMQTemplate;
    private final CartHandler cartHandler;
    private final CartRepository cartRepository;
    private final CheckoutCartRepository checkoutCartRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;
    private final PlacedOrderPublisher placedOrderPublisher;

    @Transactional
    @Cacheable(cacheNames = "Address")
    public void updateAddress(long customerId, @Valid Address address){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updateAddress(address));
    }

    @Transactional
    @Cacheable(cacheNames = "PaymentMethod")
    public void updatePaymentMethod(long customerId, @Valid PaymentMethod paymentMethod){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updatePaymentMethod(paymentMethod));
    }

    public CheckoutCartQueryResponse getCheckoutCart(long customerId) {
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        return CheckoutCartQueryResponse.toResponse(customerCheckoutCart);
    }

    @Transactional
    public void cancelCheckoutCart(long customerId) {
        findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.deleteCheckoutCartByCustomerId(customerId);
    }

    @Transactional
    void order(long customerId){
        Cart customerCart = cartHandler.findCartByCustomerId(customerId);
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);

        if (!customerCheckoutCart.hasEnoughDataToPayAndDeliver()) throw new NotEnoughDataToPayAndDeliverException();

        Order newOrder = customerCheckoutCart.placeOrder();
        orderRepository.save(newOrder);
        List<OrderDetail> orderDetails = customerCart.mapToOrderDetails(newOrder.getOrderId());
        orderDetailsRepository.saveAll(orderDetails);

        orderMQTemplate.publishPayment(customerCart, customerCheckoutCart, newOrder.getOrderId());
        orderMQTemplate.publishBooksDecrement(orderDetails);
        orderMQTemplate.publishDelivery(newOrder.getOrderId(), customerCheckoutCart.getAddress());

        checkoutCartRepository.deleteCheckoutCartByCustomerId(customerId);
        cartRepository.deleteCartByCustomerId(customerId);

        placedOrderPublisher.publishPlacedOrderEvent(customerCart, customerCheckoutCart, newOrder);
    }

    private CheckoutCart findCheckoutCartByCustomerId(long customerId){
        return checkoutCartRepository.findByCart_CustomerId(customerId).orElseThrow( () -> {
            log.warn("Checkout cart for customer with id {} does not exist", customerId);
            throw new CheckoutCartNotFoundException();
        });
    }
}
