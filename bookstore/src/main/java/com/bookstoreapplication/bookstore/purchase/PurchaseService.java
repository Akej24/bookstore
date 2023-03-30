package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.BookFacade;
import com.bookstoreapplication.bookstore.user.account.User;
import com.bookstoreapplication.bookstore.user.account.UserRepository;
import com.bookstoreapplication.bookstore.user.account.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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
    private final UserService userService;
    private final BookFacade bookFacade;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;

    @Transactional
    @Cacheable(cacheNames = "Purchase")
    public void createNewPurchase(@Valid PurchaseRequest purchaseRequest) {

        User user = userService.findUserById(purchaseRequest.getUserId());
        checkUserHasInitializedPurchase(user.getUserId());

        Purchase newPurchase = createNewPurchaseForUser(user);
        logger.info("Purchase prepared");

        BigDecimal totalPrice = BigDecimal.ZERO;
        var purchaseDetails = new ArrayList<PurchaseDetail>();
        for(PurchaseDetailRequest detail : purchaseRequest.getPurchaseDetailsRequest()){
            bookFacade.updateBookAvailablePieces(detail.getBooksAmount(), detail.getBookId());
            purchaseDetails.add(createNewPurchaseDetail(newPurchase, detail));
            totalPrice = totalPrice.add(bookFacade.calculateBookPriceByAmount(detail.getBookId(), detail.getBooksAmount()));
        }
        logger.info("Price calculated and updated available pieces");

        purchaseDetailRepository.saveAll(purchaseDetails);
        logger.info("All details have been successfully saved");

        Purchase purchaseWithTotalPrice = newPurchase.toBuilder().totalPrice(totalPrice.doubleValue()).build();
        purchaseRepository.save(purchaseWithTotalPrice);
        logger.info("Purchase has been successfully created and initialized");
    }

    @Transactional
    @Cacheable(cacheNames = "Payment")
    public void payForPurchase(@Valid UserIdRequest userIdRequest) {
        Long userId = userIdRequest.getUserId();

        User user = userService.findUserById(userId);
        Purchase purchase = getUserInitializedPurchase(userId);
        logger.info("Successfully found one initialized user purchase with id {}", purchase.getPurchaseId());

        isUserAvailableToPay(user, purchase);
        logger.info("User {} is available to pay for purchase with id {}", userId, purchase.getPurchaseId());

        updateUserFunds(user, purchase);
        logger.info("User {} successfully paid for purchase with id {}", userId, purchase.getPurchaseId());

        Purchase succeededPurchase = purchase.toBuilder().purchaseStatus(PurchaseStatus.SUCCEED).build();
        purchaseRepository.save(succeededPurchase);
    }

    @Transactional
    @Cacheable(cacheNames = "Purchase")
    public void cancelPurchase(@Valid UserIdRequest userIdRequest){
        Purchase purchase = getUserInitializedPurchase(userIdRequest.getUserId());

        purchaseDetailRepository.deleteByPurchasePurchaseId(purchase.getPurchaseId());
        logger.info("Successfully deleted all details considered with purchase with id {}", purchase.getPurchaseId());

        Purchase canceledPurchase = purchase.toBuilder().purchaseStatus(PurchaseStatus.CANCELED).build();
        purchaseRepository.save(canceledPurchase);
        logger.info("Canceled purchase status with id {} for user {}", purchase.getPurchaseId(), userIdRequest.getUserId());

    }

    @Transactional
    @Cacheable(cacheNames = "Purchases")
    public List<PurchaseResponse> getAllPurchases(@Valid UserIdRequest userIdRequest) {
        User user = userService.findUserById(userIdRequest.getUserId());
        List<Purchase> purchases = purchaseRepository.findByUserUserId(user.getUserId());
        return PurchaseResponseMapper.mapToPurchasesResponse(purchases);
    }

    @Transactional
    @Cacheable(cacheNames = "PurchasesWithDetails")
    public List<PurchaseResponse> getAllPurchasesWithDetails(@Valid UserIdRequest userIdRequest) {
        User user = userService.findUserById(userIdRequest.getUserId());
        List<Purchase> purchases = purchaseRepository.findByUserUserId(user.getUserId());
        return PurchaseResponseMapper.mapToPurchasesWithDetailsResponse(purchases);
    }

    private void updateUserFunds(User user, Purchase purchase) {
        BigDecimal userFunds = BigDecimal.valueOf(user.getAvailableFunds());
        BigDecimal totalPrice = BigDecimal.valueOf(purchase.getTotalPrice());
        Double userNewFunds = userFunds.subtract(totalPrice).doubleValue();
        User userWithNewFunds = user.toBuilder().availableFunds(userNewFunds).build();
        userRepository.save(userWithNewFunds);
    }

    private void isUserAvailableToPay(User user, Purchase purchase) {
        if(user.getAvailableFunds()< purchase.getTotalPrice()){
            logger.warn("User with id {} does not have enough funds to pay for purchase with id {}", user.getUserId(), purchase.getPurchaseId());
            throw new IllegalArgumentException("User with given id does not have enough funds to pay for this purchase");
        }
    }

    private Purchase getUserInitializedPurchase(Long userId){
        List<Purchase> initializedPurchases = purchaseRepository.findByUserUserIdAndPurchaseStatus(userId, PurchaseStatus.INITIALIZED);
        if(initializedPurchases.isEmpty()){
            logger.warn("User does not have any initialized purchase");
            throw new IllegalArgumentException("User do not have any initialized purchase");
        } else if(initializedPurchases.size()>1){
            logger.error("User has more than one initialized purchase [Important error]");
            throw new IllegalArgumentException("User has more than one initialized purchase, contact with support");
        }
        return initializedPurchases.get(0);
    }

    private PurchaseDetail createNewPurchaseDetail(Purchase newPurchase, PurchaseDetailRequest detail) {
        return PurchaseDetail.builder()
                .book(bookFacade.createNewSimpleBookQueryDto(detail.getBookId()))
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

}
