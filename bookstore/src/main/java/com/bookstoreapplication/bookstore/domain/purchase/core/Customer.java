package com.bookstoreapplication.bookstore.domain.purchase;

import com.bookstoreapplication.bookstore.domain.purchase.exception.UserDoesNotHaveAnyInitializedPurchasesException;
import com.bookstoreapplication.bookstore.domain.purchase.exception.UserHasAlreadyInitializedPurchaseException;
import com.bookstoreapplication.bookstore.domain.purchase.exception.UserHasMoreThanOneInitializedPurchaseException;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.Funds;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.TotalPrice;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.SimpleCustomerId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "users")
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Customer implements Serializable {

        private SimpleCustomerId customerId;
        private Funds funds;

        boolean isAbleToPay(TotalPrice totalPrice){
                return totalPrice.totalPrice().compareTo(funds.funds()) <= 0;
        }

        void decreaseFunds(TotalPrice totalPrice){
                BigDecimal newFunds = funds.funds().subtract(totalPrice.totalPrice());
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
