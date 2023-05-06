package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.exception.CustomerDoesNotHaveAnyInitializedOrderException;
import com.bookstoreapplication.bookstore.purchase.exception.CustomerHasAlreadyInitializedOrderException;
import com.bookstoreapplication.bookstore.purchase.exception.CustomerHasMoreThanOneInitializedOrderException;
import com.bookstoreapplication.bookstore.purchase.value_object.Funds;
import com.bookstoreapplication.bookstore.purchase.value_object.TotalPrice;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
class Customer implements Serializable {

    private long customerId;
    private Funds funds;

    boolean isAbleToPay(TotalPrice totalPrice){
        return totalPrice.getTotalPrice().compareTo(funds.getFunds()) <= 0;
    }

    void decreaseFunds(TotalPrice totalPrice){
        BigDecimal newFunds = funds.getFunds().subtract(totalPrice.getTotalPrice());
        funds = new Funds(newFunds);
    }

    void checkIfHasAnyInitializedPurchase(List<Order> initializedOrders) {
        if(!initializedOrders.isEmpty()){
            throw new CustomerHasAlreadyInitializedOrderException();
        }
    }

    Order getInitializedPurchase(List<Order> initializedOrders){
        if(initializedOrders.isEmpty()){
            throw new CustomerDoesNotHaveAnyInitializedOrderException();
        } else if(initializedOrders.size()>1){
            throw new CustomerHasMoreThanOneInitializedOrderException();
        }
        return initializedOrders.get(0);
    }

}
