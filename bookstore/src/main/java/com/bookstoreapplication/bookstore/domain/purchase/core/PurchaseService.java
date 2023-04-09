package com.bookstoreapplication.bookstore.domain.purchase;

import com.bookstoreapplication.bookstore.domain.purchase.value_objects.RequestDetail;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.SimpleCustomerId;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.SimplePurchaseId;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.PurchaseStatus;
import com.bookstoreapplication.bookstore.purchase.value_objects.*;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class PurchaseService {

    public Set<PurchaseDetail> createDetails(Set<RequestDetail> requestDetails, SimplePurchaseId purchaseId) {
        var details = new HashSet<PurchaseDetail>();
        for(RequestDetail requestDetail : requestDetails){
            PurchaseDetail toAdd = new PurchaseDetail(requestDetail.bookId(), requestDetail.booksAmount(), purchaseId);
            details.add(toAdd);
        }
        return details;
    }

    @Transactional
    @Cacheable(cacheNames = "Payment")
    public void payForPurchase(SimpleCustomerId simpleCustomerId) {

        Customer customer = findCustomerById(simpleCustomerId);
        List<Purchase> customerInitializedPurchases = purchaseRepository.findByCustomer_CustomerIdAndPurchaseStatusInitialized(simpleCustomerId.customerId());
        Purchase purchase = customer.getInitializedPurchase(customerInitializedPurchases);

        if(!customer.isAbleToPay(purchase.getTotalPrice())){
            throw new IllegalArgumentException("User with given id is not able to pay");
        }

        customer.decreaseFunds(purchase.getTotalPrice());
        purchase.setPurchaseStatus(PurchaseStatus.SUCCEED);
    }

    @Transactional
    @Cacheable(cacheNames = "Purchase")
    public void cancelPurchase(long userIdRequest){
        //
    }

    @Transactional
    @Cacheable(cacheNames = "Purchases")
    public List<Purchase> getAllPurchases(long userIdRequest) {
        //
        return null;
    }

    @Transactional
    @Cacheable(cacheNames = "PurchasesWithDetails")
    public List<Purchase> getAllPurchasesWithDetails(long userIdRequest) {
        //
        return null;
    }

}
