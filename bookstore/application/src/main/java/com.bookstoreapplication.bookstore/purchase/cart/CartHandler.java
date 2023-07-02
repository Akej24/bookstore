package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.purchase.exception.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
public class CartHandler {

    private final CartRepository cartRepository;
    private final BookProductRepository bookProductRepository;

    public CartQueryResponse getCart(long customerId) {
        Cart customerCart = findCartByCustomerId(customerId);
        return CartQueryResponse.toResponse(customerCart);
    }

    @Transactional
    public void addProduct(long customerId, long bookId) {
        BookProduct bookProduct = findBookById(bookId);
        if(cartRepository.existsByCustomerId(customerId)){
            Cart customerCart = findCartByCustomerId(customerId);
            cartRepository.save(customerCart.addProduct(bookProduct));
        } else {
            cartRepository.save(new Cart(customerId, bookProduct));
        }
    }

    @Transactional
    public void deleteProduct(long customerId, long bookId) {
        Cart customerCart = findCartByCustomerId(customerId);
        Cart cartWithDeletedProduct = customerCart.deleteProduct(bookId);
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
    public void cleanCart(long customerId){
        cartRepository.deleteCartByCustomerId(customerId);
    }

    public void areCartLinesAbleToOrder(Cart customerCart){
        customerCart.getCartLines().forEach(this::isCartLineAbleToOrder);
    }

    private void isCartLineAbleToOrder(CartLine cartLine){
        BookProduct bookProduct = findBookById(cartLine.getBookProduct().getBookId());
        cartLine.isAbleToOrder(bookProduct.getAvailablePieces().getAvailablePieces());
    }

    public Cart findCartByCustomerId(long customerId){
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
