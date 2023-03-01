package com.bookstoreapplication.bookstore.book;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
class BookRequest {

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
    private Boolean status;

    @Min(value = 0, message = "Available pieces must not be null and the minimum value is 0")
    @NotNull
    private Integer availablePieces;

    @DecimalMin(value = "0.0", message = "Price must be not null and the minimum value is 0.0")
    @NotNull
    private Double price;

}
