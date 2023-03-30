package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.query.SimpleBookQueryDto;
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
@AllArgsConstructor
@Builder(toBuilder = true)
class PurchaseDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchaseDetailId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private SimpleBookQueryDto book;

    @Min(value = 1, message = "The minimum value of books amount is 1")
    @NotNull(message = "Books amount cannot be null")
    private Integer booksAmount;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;
}
