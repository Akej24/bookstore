package com.bookstoreapplication.bookstore.purchase.query;

import com.bookstoreapplication.bookstore.book.query.SimpleBookQueryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "purchase_details")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimplePurchaseDetailQueryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchaseDetailId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private SimpleBookQueryDto book;

}
