package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.Book;
import com.bookstoreapplication.bookstore.book.BookRepository;
import com.bookstoreapplication.bookstore.book.BookService;
import com.bookstoreapplication.bookstore.user.account.User;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@AllArgsConstructor
@Validated
public class PurchaseService {

    private final BookService bookService;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailsRepository purchaseDetailsRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    PurchaseDatabaseModel createNewPurchase(@Valid PurchaseRequest purchaseRequest) {
        var newPurchase = buildNewPurchase(purchaseRequest);
        logger.info("Successfully created new purchase");
        return purchaseRepository.save(newPurchase);
    }

    private PurchaseDatabaseModel buildNewPurchase(PurchaseRequest purchaseRequest) {
        User user = userRepository
                .findById(purchaseRequest.getUserId())
                .orElseThrow( () -> new IllegalArgumentException("User with given id does not exist"));

        Book book = bookRepository
                .findById(purchaseRequest.getBookId())
                .orElseThrow( () -> new IllegalArgumentException("Book with given id does not exist"));

        PurchaseDetails purchaseDetails = PurchaseDetails.builder()
                .book(book)
                .booksAmount(purchaseRequest.getBooksAmount())
                .build();
        purchaseDetailsRepository.save(purchaseDetails);

        var purchaseDetailsSet = new HashSet<PurchaseDetails>();
        purchaseDetailsSet.add(purchaseDetails);

        return PurchaseDatabaseModel.builder()
                .user(user)
                .purchaseDetails(purchaseDetailsSet)
                .purchaseDate(LocalDateTime.now())
                .totalPrice(bookService.calculateBookPriceByAmount(purchaseRequest.getBookId(), purchaseRequest.getBooksAmount()))
                .purchaseStatus(PurchaseStatus.INITIALIZED)
                .build();
    }

}
