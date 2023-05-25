package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.cart.CartHandler;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.CheckoutCart;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.CheckoutCartHandler;
import com.bookstoreapplication.bookstore.purchase.exception.NotEnoughDataToPayAndDeliverException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
class OrderHandler {

    private final CartHandler cartHandler;
    private final CheckoutCartHandler checkoutCartHandler;
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;
    private final OrderMQTemplate orderMQTemplate;
    private final PlacedOrderPublisher placedOrderPublisher;

    @Transactional
    void order(long customerId){
        Cart customerCart = cartHandler.findCartByCustomerId(customerId);
        CheckoutCart customerCheckoutCart = checkoutCartHandler.findCheckoutCartByCustomerId(customerId);

        if (!customerCheckoutCart.hasEnoughDataToPayAndDeliver()) throw new NotEnoughDataToPayAndDeliverException();

        Order newOrder = new Order(customerCheckoutCart);
        orderRepository.save(newOrder);
        List<OrderDetail> orderDetails = OrderDetail.mapFromCart(newOrder.getOrderId(), customerCart);
        orderDetailsRepository.saveAll(orderDetails);

        orderMQTemplate.publishPayment(customerCart, customerCheckoutCart, newOrder.getOrderId());
        orderMQTemplate.publishBooksDecrement(orderDetails);
        orderMQTemplate.publishDelivery(newOrder.getOrderId(), customerCheckoutCart.getAddress());

        checkoutCartHandler.cancelCheckoutCart(customerId);
        cartHandler.cleanCart(customerId);

        placedOrderPublisher.publishPlacedOrderEvent(customerCart, customerCheckoutCart, newOrder);
    }

    @Transactional
    public Set<OrderQueryResponse> getCustomerOrders(long customerId){
        List<Order> customerOrders = orderRepository.findAllByCustomerId(customerId);
        log.info("All orders for user with id {} has been fetched from database", customerId);
        return OrderQueryResponse.toResponses(customerOrders);
    }

}
