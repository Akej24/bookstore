package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.BookFacade;
import com.bookstoreapplication.bookstore.purchase.vo.PurchaseStatus;
import com.bookstoreapplication.bookstore.user.UserFacade;
import com.bookstoreapplication.bookstore.user.SimpleUserQueryDto;
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
class PurchaseService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PurchaseService.class);
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final PurchaseRepository purchaseRepository;
    private final BookFacade bookFacade;
    private final UserFacade userFacade;

    @Transactional
    @Cacheable(cacheNames = "Purchase")
    public void createNewPurchase(@Valid PurchaseRequest purchaseRequest) {

        SimpleUserQueryDto user = userFacade.getSimpleUserQueryDto(purchaseRequest.getUserId());
        checkUserHasInitializedPurchase(user.getUserId());

        Purchase newPurchase = createNewPurchaseForUser(user);
        LOGGER.info("Purchase prepared");

        BigDecimal totalPrice = BigDecimal.ZERO;
        var purchaseDetails = new ArrayList<PurchaseDetail>();
        for(PurchaseDetailRequest detail : purchaseRequest.getPurchaseDetailsRequest()){
            bookFacade.updateBookAvailablePieces(detail.getBooksAmount(), detail.getBookId());
            purchaseDetails.add(createNewPurchaseDetail(newPurchase, detail));
            totalPrice = totalPrice.add(bookFacade.calculateBookPriceByAmount(detail.getBookId(), detail.getBooksAmount()));
        }
        LOGGER.info("Price calculated and updated available pieces");

        purchaseDetailRepository.saveAll(purchaseDetails);
        LOGGER.info("All details have been successfully saved");

        Purchase purchaseWithTotalPrice = newPurchase.toBuilder().totalPrice(totalPrice.doubleValue()).build();
        purchaseRepository.save(purchaseWithTotalPrice);
        LOGGER.info("Purchase has been successfully created and initialized");
    }

    @Transactional
    @Cacheable(cacheNames = "Payment")
    public void payForPurchase(@Valid UserIdRequest userIdRequest) {
        Long userId = userIdRequest.getUserId();

        Purchase purchase = getUserInitializedPurchase(userId);
        LOGGER.info("Successfully found one initialized user purchase with id {}", purchase.getPurchaseId());

        userFacade.isUserAvailableToPay(userId, purchase.getTotalPrice());
        LOGGER.info("User {} is available to pay for purchase with id {}", userId, purchase.getPurchaseId());

        userFacade.updateUserFunds(userId, purchase.getTotalPrice());
        LOGGER.info("User {} successfully paid for purchase with id {}", userId, purchase.getPurchaseId());

        Purchase succeededPurchase = purchase.toBuilder().purchaseStatus(PurchaseStatus.SUCCEED).build();
        purchaseRepository.save(succeededPurchase);
    }

    @Transactional
    @Cacheable(cacheNames = "Purchase")
    public void cancelPurchase(@Valid UserIdRequest userIdRequest){
        Purchase purchase = getUserInitializedPurchase(userIdRequest.getUserId());

        purchaseDetailRepository.deleteByPurchasePurchaseId(purchase.getPurchaseId());
        LOGGER.info("Successfully deleted all details considered with purchase with id {}", purchase.getPurchaseId());

        Purchase canceledPurchase = purchase.toBuilder().purchaseStatus(PurchaseStatus.CANCELED).build();
        purchaseRepository.save(canceledPurchase);
        LOGGER.info("Canceled purchase status with id {} for user {}", purchase.getPurchaseId(), userIdRequest.getUserId());

    }

    @Transactional
    @Cacheable(cacheNames = "Purchases")
    public List<PurchaseDto> getAllPurchases(@Valid UserIdRequest userIdRequest) {
        userFacade.existsUserById(userIdRequest.getUserId());
        List<Purchase> purchases = purchaseRepository.findByUserUserId(userIdRequest.getUserId());
        return PurchaseResponseMapper.mapToPurchasesResponse(purchases);
    }

    @Transactional
    @Cacheable(cacheNames = "PurchasesWithDetails")
    public List<PurchaseDto> getAllPurchasesWithDetails(@Valid UserIdRequest userIdRequest) {
        userFacade.existsUserById(userIdRequest.getUserId());
        List<Purchase> purchases = purchaseRepository.findByUserUserId(userIdRequest.getUserId());
        return PurchaseResponseMapper.mapToPurchasesWithDetailsResponse(purchases);
    }

    private Purchase getUserInitializedPurchase(Long userId){
        List<Purchase> initializedPurchases = purchaseRepository.findByUserUserIdAndPurchaseStatus(userId, PurchaseStatus.INITIALIZED);
        if(initializedPurchases.isEmpty()){
            LOGGER.warn("User does not have any initialized purchase");
            throw new IllegalArgumentException("User do not have any initialized purchase");
        } else if(initializedPurchases.size()>1){
            LOGGER.error("User has more than one initialized purchase [Important error]");
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

    private Purchase createNewPurchaseForUser(SimpleUserQueryDto user) {
        Purchase newPurchase = Purchase.builder().user(user).build();
        return purchaseRepository.save(newPurchase);
    }

    private void checkUserHasInitializedPurchase(Long userId) {
        if(!purchaseRepository.findByUserUserIdAndPurchaseStatus(userId, PurchaseStatus.INITIALIZED).isEmpty()){
            LOGGER.warn("User with id {} has already initialized purchase", userId);
            throw new IllegalArgumentException("User with given id has already initialized purchase");
        }
    }

}
