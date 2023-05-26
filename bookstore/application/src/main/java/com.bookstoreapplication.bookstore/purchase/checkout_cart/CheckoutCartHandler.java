package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.cart.CartHandler;
import com.bookstoreapplication.bookstore.purchase.exception.CheckoutCartNotFoundException;
import com.bookstoreapplication.bookstore.purchase.exception.CustomerHasAlreadyInitializedCheckoutCartException;
import com.bookstoreapplication.bookstore.purchase.value_object.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Slf4j
@Validated
@AllArgsConstructor
public class CheckoutCartHandler {

    private final CartHandler cartHandler;
    private final CheckoutCartRepository checkoutCartRepository;

    @Transactional
    public void checkout(long customerId){
        if(checkoutCartRepository.existsByCart_CustomerId(customerId)){
            log.warn("Customer has already initialized checkout cart");
            throw new CustomerHasAlreadyInitializedCheckoutCartException();
        }
        Cart customerCart = cartHandler.findCartByCustomerId(customerId);
        checkoutCartRepository.save(new CheckoutCart(customerCart));
    }

    @Transactional
    public void updateAddress(long customerId, @Valid Address address){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updateAddress(address));
    }

    @Transactional
    public void updatePaymentMethod(long customerId, @Valid PaymentMethod paymentMethod){
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCheckoutCart.updatePaymentMethod(paymentMethod));
    }

    public CheckoutCartQueryResponse getCheckoutCart(long customerId) {
        CheckoutCart customerCheckoutCart = findCheckoutCartByCustomerId(customerId);
        return CheckoutCartQueryResponse.from(customerCheckoutCart);
    }

    @Transactional
    public void cancelCheckoutCart(long customerId) {
        findCheckoutCartByCustomerId(customerId);
        checkoutCartRepository.deleteCheckoutCartByCustomerId(customerId);
    }

    public CheckoutCart findCheckoutCartByCustomerId(long customerId){
        return checkoutCartRepository.findByCart_CustomerId(customerId).orElseThrow( () -> {
            log.warn("Checkout cart for customer with id {} does not exist", customerId);
            throw new CheckoutCartNotFoundException();
        });
    }
}
