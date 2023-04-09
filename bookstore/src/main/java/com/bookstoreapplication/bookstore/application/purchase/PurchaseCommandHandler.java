package com.bookstoreapplication.bookstore.application.purchase;

import com.bookstoreapplication.bookstore.adapters.purchase.BookProductRepository;
import com.bookstoreapplication.bookstore.adapters.purchase.CustomerJpaRepository;
import com.bookstoreapplication.bookstore.adapters.purchase.PurchaseDetailJpaRepository;
import com.bookstoreapplication.bookstore.adapters.purchase.PurchaseJpaRepository;
import com.bookstoreapplication.bookstore.domain.purchase.core.BookProduct;
import com.bookstoreapplication.bookstore.domain.purchase.core.Customer;
import com.bookstoreapplication.bookstore.domain.purchase.core.Purchase;
import com.bookstoreapplication.bookstore.domain.purchase.core.PurchaseService;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class PurchaseCommandHandler {

    private final PurchaseService purchaseService;
    private final PurchaseDetailJpaRepository purchaseDetailJpaRepository;
    private final BookProductRepository bookInOrderRepository;
    private final CustomerJpaRepository customerRepository;
    private final PurchaseJpaRepository purchaseJpaRepository;

    @Transactional
    @Cacheable(cacheNames = "Purchase")
    public void placeOrder(PurchaseCommand request) {

        Customer customer = findCustomerById(request.getCustomerId().getUserId());
        List<Purchase> customerInitializedPurchases = purchaseJpaRepository.findByUserId_UserIdAndPurchaseStatus(customer.getCustomerId().getUserId(), PurchaseStatus.INITIALIZED);
        HashMap<BookProduct, BooksAmount> orderedBooks = findBooksById(request);

        Purchase createdPurchase = purchaseService.placeOrder(request.getPurchaseCommandDetails(), customer, customerInitializedPurchases, orderedBooks);
        purchaseJpaRepository.save(createdPurchase);
        //purchaseDetailJpaRepository.saveAll(details);
        log.info("Successfully placed order");
    }

    @Transactional
    @Cacheable(cacheNames = "Payment")
    public void payForPurchase(SimpleCustomerId simpleCustomerId) {
    }

    private HashMap<BookProduct, BooksAmount> findBooksById(PurchaseCommand request) {
        HashMap<BookProduct, BooksAmount> orderedBooks = new HashMap<>();
        request.getPurchaseCommandDetails().forEach(requestDetail -> {
            orderedBooks.put(findBookById(requestDetail.bookId().getBookId()), requestDetail.booksAmount());
        });
        return orderedBooks;
    }

    private BookProduct findBookById(Long bookId) {
        return bookInOrderRepository.findById(bookId).orElseThrow( () -> {
            log.warn("Book with id {} does not exist", bookId);
            throw new IllegalArgumentException("Book with given id does not exist");
        });
    }

    private Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow( () -> {
            log.warn("Customer with id {} does not exist", customerId);
            throw new IllegalArgumentException("Customer with given id does not exist");
        });
    }
}
