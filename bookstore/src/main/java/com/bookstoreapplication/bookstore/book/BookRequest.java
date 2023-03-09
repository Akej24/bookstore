package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
