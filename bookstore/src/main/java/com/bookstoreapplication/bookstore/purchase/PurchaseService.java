package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.BookRepository;
import com.bookstoreapplication.bookstore.book.BookService;
import com.bookstoreapplication.bookstore.user.account.User;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class PurchaseService {

    private final Logger logger = LoggerFactory.getLogger(PurchaseService.class);
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final EntityManager entityManager;

    @Transactional
    @Cacheable(cacheNames = "PurchaseDetail")
    public void createNewPurchase(@Valid PurchaseRequest purchaseRequest) {

        User user = userRepository.findById(purchaseRequest.getUserId()).orElseThrow( () -> {
            logger.warn("User with id {} has not been found", purchaseRequest.getUserId());
            throw new IllegalArgumentException("User with given id does not exist");
        });

        Purchase newPurchase = Purchase.builder().user(user).build();
        purchaseRepository.save(newPurchase);

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<PurchaseDetail> purchaseDetails = new ArrayList<>();
        for(PurchaseDetailRequest detail : purchaseRequest.getPurchaseDetailsRequest()){
            purchaseDetails.add(buildPurchaseDetail(detail, newPurchase));
            totalPrice = totalPrice.add(bookService.calculateBookPriceByAmount(detail.getBookId(), detail.getBooksAmount()));
        }
        purchaseDetailRepository.saveAll(purchaseDetails);

        Purchase purchaseWithCalculatedPrice = newPurchase.toBuilder().totalPrice(totalPrice.doubleValue()).build();
        purchaseRepository.save(purchaseWithCalculatedPrice);

        //check if user has initialized purchase


    }

    private PurchaseDetail buildPurchaseDetail(PurchaseDetailRequest detail, Purchase purchase) {
        return PurchaseDetail.builder()
                .book(bookRepository
                        .findById(detail.getBookId()).orElseThrow( () -> new IllegalArgumentException("Book with given id does not exist"))
                )
                .booksAmount(detail.getBooksAmount())
                .purchase(purchase)
                .build();
    }

}
