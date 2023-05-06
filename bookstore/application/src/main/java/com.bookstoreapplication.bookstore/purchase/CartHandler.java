package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.exception.BookProductNotFoundException;
import com.bookstoreapplication.bookstore.purchase.exception.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
class CartHandler {

    private final CartRepository cartRepository;
    private final CheckoutCartRepository checkoutCartRepository;
    private final BookProductRepository bookInOrderRepository;
    private final CustomerRepository customerRepository;

    void initializeCart(long customerId, long bookId) {
        BookProduct bookProduct = findBookById(bookId);
        cartRepository.save(new Cart(customerId, bookProduct));
    }

    void addProduct(long customerId, long bookId) {
        BookProduct bookProduct = findBookById(bookId);
        Cart customerCart = cartRepository.findByCustomerId(customerId);
        cartRepository.save(customerCart.addProduct(bookProduct));
    }

    void deleteProduct(long customerId, long bookId) {
        BookProduct bookProduct = findBookById(bookId);
        Cart customerCart = cartRepository.findByCustomerId(customerId);
        cartRepository.save(customerCart.deleteProduct(bookProduct));
    }

    void increaseProductAmount(long customerId, long bookId) {
        BookProduct bookProduct = findBookById(bookId);
        Cart customerCart = cartRepository.findByCustomerId(customerId);
        cartRepository.save(customerCart.increaseProductAmount(bookProduct));
    }

    void decreaseProductAmount(long customerId, long bookId) {
        BookProduct bookProduct = findBookById(bookId);
        Cart customerCart = cartRepository.findByCustomerId(customerId);
        cartRepository.save(customerCart.decreaseProductAmount(bookProduct));
    }

    void checkout(long customerId){
        Cart customerCart = cartRepository.findByCustomerId(customerId);
        checkoutCartRepository.save(customerCart.checkout());
    }

    private BookProduct findBookById(long bookId) {
        return bookInOrderRepository.findById(bookId).orElseThrow( () -> {
            log.warn("Book with id {} does not exist", bookId);
            throw new BookProductNotFoundException();
        });
    }

    private Customer findCustomerById(long customerId) {
        return customerRepository.findById(customerId).orElseThrow( () -> {
            log.warn("Customer with id {} does not exist", customerId);
            throw new CustomerNotFoundException();
        });
    }
}
