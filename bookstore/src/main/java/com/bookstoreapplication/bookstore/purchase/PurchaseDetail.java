package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.vo.BooksAmount;
import com.bookstoreapplication.bookstore.purchase.vo.PurchaseDetailId;
import com.bookstoreapplication.bookstore.purchase.vo.SimplePurchaseBook;
import com.bookstoreapplication.bookstore.purchase.vo.SimplePurchaseId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "purchase_details")
@Getter
@NoArgsConstructor
class PurchaseDetail implements Serializable {

    @Id
    private PurchaseDetailId purchaseDetailId;
    private SimplePurchaseBook simplePurchaseBook;
    private BooksAmount booksAmount;
    private SimplePurchaseId purchaseId;

}
