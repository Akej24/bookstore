package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.exception.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
class CartHandler {

    private final CartRepository cartRepository;
    private final CheckoutCartRepository checkoutCartRepository;
    private final BookProductRepository bookProductRepository;

    @Transactional
    public void initializeCart(long customerId, long bookId) {
        if(cartRepository.existsByCustomerId(customerId)){
            log.warn("Customer has already initialized cart");
            throw new CustomerHasAlreadyInitializedCartException();
        }
        BookProduct bookProduct = findBookById(bookId);
        cartRepository.save(new Cart(customerId, bookProduct));
    }

    public CartQueryResponse getCart(long customerId) {
        Cart customerCart = findCartByCustomerId(customerId);
        return CartQueryResponse.toResponse(customerCart);
    }

    @Transactional
    public void addProduct(long customerId, long bookId) {
        BookProduct bookProduct = findBookById(bookId);
        Cart customerCart = findCartByCustomerId(customerId);
        cartRepository.save(customerCart.addProduct(bookProduct));
    }

    @Transactional
    public void deleteProduct(long customerId, long bookId) {
        BookProduct bookProduct = findBookById(bookId);
        Cart customerCart = findCartByCustomerId(customerId);
        Cart cartWithDeletedProduct = customerCart.deleteProduct(bookProduct);
        if(cartWithDeletedProduct.getCartLines().isEmpty()){
            cartRepository.deleteCartByCustomerId(customerId);
        } else {
            cartRepository.save(cartWithDeletedProduct);
        }
    }

    @Transactional
    public void increaseProductAmount(long customerId, long bookId) {
        BookProduct bookProduct = findBookById(bookId);
        Cart customerCart = findCartByCustomerId(customerId);
        cartRepository.save(customerCart.increaseProductAmount(bookProduct));
    }

    @Transactional
    public void decreaseProductAmount(long customerId, long bookId) {
        BookProduct bookProduct = findBookById(bookId);
        Cart customerCart = findCartByCustomerId(customerId);
        cartRepository.save(customerCart.decreaseProductAmount(bookProduct));
    }

    @Transactional
    public void checkout(long customerId){
        if(checkoutCartRepository.existsByCart_CustomerId(customerId)){
            log.warn("Customer has already initialized checkout cart");
            throw new CustomerHasAlreadyInitializedCheckoutCartException();
        }
        Cart customerCart = findCartByCustomerId(customerId);
        checkoutCartRepository.save(customerCart.checkout());
    }

    Cart findCartByCustomerId(long customerId){
        return cartRepository.findByCustomerId(customerId).orElseThrow( () -> {
            log.warn("Customer with id {} does not have any initialized cart", customerId);
            throw new CartNotFoundException();
        });
    }

    private BookProduct findBookById(long bookId) {
        return bookProductRepository.findById(bookId).orElseThrow( () -> {
            log.warn("Book with id {} does not exist", bookId);
            throw new BookProductNotFoundException();
        });
    }
}
