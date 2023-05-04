package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import com.bookstoreapplication.bookstore.purchase.value_object.PurchaseStatus;
import com.bookstoreapplication.bookstore.purchase.value_object.SimpleCustomerId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
class PurchaseCommandHandler {

    private final PurchaseService purchaseService;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final BookProductRepository bookInOrderRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    @Transactional
    @Cacheable(cacheNames = "Purchase")
    public void placeOrder(PurchaseCommand request) {

        Customer customer = findCustomerById(request.getCustomerId().getUserId());
        List<Purchase> customerInitializedPurchases = purchaseRepository.findByUserId_UserIdAndPurchaseStatus(customer.getCustomerId().getUserId(), PurchaseStatus.INITIALIZED);
        HashMap<BookProduct, BooksAmount> orderedBooks = findBooksById(request);

        Purchase createdPurchase = purchaseService.placeOrder(request.getPurchaseCommandDetails(), customer, customerInitializedPurchases, orderedBooks);
        purchaseRepository.save(createdPurchase);
        //purchaseDetailRepository.saveAll(details);
        log.info("Successfully placed order");
    }

    @Transactional
    @Cacheable(cacheNames = "Payment")
    public void payForPurchase(SimpleCustomerId simpleCustomerId) {
    }

    private HashMap<BookProduct, BooksAmount> findBooksById(PurchaseCommand request) {
        HashMap<BookProduct, BooksAmount> orderedBooks = new HashMap<>();
        for (PurchaseCommandDetail requestDetail : request.getPurchaseCommandDetails()) {
            orderedBooks.put(findBookById(requestDetail.bookId().getBookId()), requestDetail.booksAmount());
        }
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
