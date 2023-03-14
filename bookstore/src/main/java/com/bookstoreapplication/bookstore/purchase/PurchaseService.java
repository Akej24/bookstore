package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.Book;
import com.bookstoreapplication.bookstore.book.BookRepository;
import com.bookstoreapplication.bookstore.user.account.User;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Service
@Validated
@AllArgsConstructor
public class PurchaseService {

    private final Logger logger = LoggerFactory.getLogger(PurchaseService.class);
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;

    @Transactional
    @Cacheable(cacheNames = "PurchaseDetail")
    public void createNewPurchase(@Valid PurchaseRequest purchaseRequest) {

        User user = findUserById(purchaseRequest.getUserId());
        checkUserHasInitializedPurchase(user.getUserId());

        Purchase newPurchase = createNewPurchaseForUser(user);

        BigDecimal totalPurchasePrice = calculateTotalPriceAndUpdateAvailablePieces(purchaseRequest, newPurchase);
        Purchase purchaseWithTotalPrice = newPurchase.toBuilder().totalPrice(totalPurchasePrice.doubleValue()).build();
        purchaseRepository.save(purchaseWithTotalPrice);

    }

    private BigDecimal calculateTotalPriceAndUpdateAvailablePieces(PurchaseRequest purchaseRequest, Purchase newPurchase) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        var purchaseDetails = new ArrayList<PurchaseDetail>();

        for(PurchaseDetailRequest detail : purchaseRequest.getPurchaseDetailsRequest()){
            Book book = findBookById(detail.getBookId());
            updateBookAvailablePieces(detail, book);
            purchaseDetails.add(createNewPurchaseDetail(newPurchase, detail, book));
            totalPrice = totalPrice.add(calculateBookPriceByAmount(book, detail.getBooksAmount()));
        }
        purchaseDetailRepository.saveAll(purchaseDetails);
        return totalPrice;
    }

    private void updateBookAvailablePieces(PurchaseDetailRequest detail, Book book) {
        if(book.getAvailablePieces() < detail.booksAmount){
            throw new IllegalArgumentException("Not enough available pieces to buy this product");
        }else{
            Book updatedBook = book.toBuilder().availablePieces(book.getAvailablePieces() - detail.booksAmount).build();
            bookRepository.save(updatedBook);
        }
    }

    private BigDecimal calculateBookPriceByAmount(Book book, Integer amount){
        return BigDecimal
                .valueOf(book.getPrice())
                .multiply(BigDecimal.valueOf(amount))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private PurchaseDetail createNewPurchaseDetail(Purchase newPurchase, PurchaseDetailRequest detail, Book book) {
        return PurchaseDetail.builder()
                .book(book)
                .booksAmount(detail.getBooksAmount())
                .purchase(newPurchase)
                .build();
    }

    private Purchase createNewPurchaseForUser(User user) {
        Purchase newPurchase = Purchase.builder().user(user).build();
        return purchaseRepository.save(newPurchase);
    }

    private void checkUserHasInitializedPurchase(Long userId) {
        if(!purchaseRepository.findByUserUserIdAndPurchaseStatus(userId, PurchaseStatus.INITIALIZED).isEmpty()){
            logger.warn("User with id {} has already initialized purchase", userId);
            throw new IllegalArgumentException("User with given id has already initialized purchase");
        }
    }

    private Book findBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow( () -> {
            logger.warn("Book with id {} does not exist", bookId);
            throw new IllegalArgumentException("Book with given id does not exist");
        });
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow( () -> {
            logger.warn("User with id {} has not been found", userId);
            throw new IllegalArgumentException("User with given id does not exist");
        });
    }

}
