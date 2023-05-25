package com.bookstoreapplication.bookstore.purchase.value_object;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class BooksAmount implements Serializable {

    @Min(value = 1, message = "The minimum value of books amount is 1")
    @NotNull(message = "Books amount cannot be null")
    private Integer booksAmount;

    public BooksAmount increaseAmount(){
        return new BooksAmount(booksAmount + 1);
    }

    public BooksAmount decreaseAmount(){
        return booksAmount > 1 ? new BooksAmount(booksAmount - 1) : this;
    }

}
