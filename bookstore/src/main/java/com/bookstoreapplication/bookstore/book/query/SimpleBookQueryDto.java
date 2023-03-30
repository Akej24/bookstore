package com.bookstoreapplication.bookstore.book.query;

import com.bookstoreapplication.bookstore.purchase.query.SimplePurchaseDetailQueryDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleBookQueryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private Set<SimplePurchaseDetailQueryDto> purchaseDetails;

    @NotBlank(message="Title must not be blank")
    private String title;

    @NotBlank(message="Author must not be blank")
    private String author;

    @NotNull(message="Release date must not be null")
    private LocalDate releaseDate;

    @Min(value = 1, message = "The minimum value of number of pages is 1")
    @NotNull(message = "Number of pages must be not null")
    private Integer numberOfPages;

    @NotNull(message = "Status mut not be null")
    @Setter
    private Boolean status;

    @Min(value = 0, message = "The minimum value of available pieces is 0")
    @NotNull(message = "Available pieces must be not null")
    private Integer availablePieces;

    @DecimalMin(value = "0.0", message = "The minimum value of the price is 0.0")
    @NotNull(message = "Price must be not null")
    private Double price;

    @PrePersist
    public void setPiecesPrePersist() {
        if(status != null && !status){
            availablePieces = 0;
        }
    }

}
