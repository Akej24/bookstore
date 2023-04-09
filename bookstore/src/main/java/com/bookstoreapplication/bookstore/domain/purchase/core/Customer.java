package com.bookstoreapplication.bookstore.domain.purchase.core;

import com.bookstoreapplication.bookstore.domain.purchase.exception.UserDoesNotHaveAnyInitializedPurchasesException;
import com.bookstoreapplication.bookstore.domain.purchase.exception.UserHasAlreadyInitializedPurchaseException;
import com.bookstoreapplication.bookstore.domain.purchase.exception.UserHasMoreThanOneInitializedPurchaseException;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.Funds;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.TotalPrice;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.SimpleCustomerId;
import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Table(name="users")
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Customer implements Serializable {

        @EmbeddedId
        private SimpleCustomerId customerId;
        @Embedded
        private Funds funds;

        boolean isAbleToPay(TotalPrice totalPrice){
                return totalPrice.getTotalPrice().compareTo(funds.getFunds()) <= 0;
        }

        void decreaseFunds(TotalPrice totalPrice){
                BigDecimal newFunds = funds.getFunds().subtract(totalPrice.getTotalPrice());
                funds = new Funds(newFunds);
        }

        void checkIfHasAnyInitializedPurchase(List<Purchase> initializedPurchases) {
                if(!initializedPurchases.isEmpty()){
                        throw new UserHasAlreadyInitializedPurchaseException();
                }
        }

        Purchase getInitializedPurchase(List<Purchase> initializedPurchases){
                if(initializedPurchases.isEmpty()){
                        throw new UserDoesNotHaveAnyInitializedPurchasesException();
                } else if(initializedPurchases.size()>1){
                        throw new UserHasMoreThanOneInitializedPurchaseException();
                }
                return initializedPurchases.get(0);
        }

}
