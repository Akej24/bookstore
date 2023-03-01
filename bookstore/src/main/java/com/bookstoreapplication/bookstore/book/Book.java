package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.purchase.PurchaseDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private Set<PurchaseDetails> purchaseDetails;

    @NotBlank(message="Title must not be blank")
    private String title;

    @NotBlank(message="Author must not be blank")
    private String author;

    @NotNull(message="Release date must not be null")
    private LocalDate releaseDate;

    @Min(value = 1, message = "Number of pages must not be null and the minimum value is 1")
    @NotNull
    private Integer numberOfPages;

    @NotNull(message = "Status mut not be null")
    @Setter
    private Boolean status;

    @Min(value = 0, message = "Available pieces must not be null and the minimum value is 0")
    @NotNull
    private Integer availablePieces;

    @DecimalMin(value = "0.0", message = "Price must be not null and the minimum value is 0.0")
    @NotNull
    private Double price;

    @Embedded
    @JsonIgnore
    private BookAudit bookAudit;

    @PrePersist
    public void setPiecesPrePersist() {
        if(status != null && !status){
            availablePieces = 0;
        }
    }
}
