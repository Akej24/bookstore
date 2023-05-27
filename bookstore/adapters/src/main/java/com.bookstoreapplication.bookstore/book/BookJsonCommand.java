package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import dev.mccue.json.Json;
import dev.mccue.json.JsonDecoder;
import dev.mccue.json.JsonEncodable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class BookJsonCommand implements JsonEncodable {

    private final BookTitle bookTitle;
    private final BookAuthor bookAuthor;
    private final ReleaseDate releaseDate;
    private final NumberOfPages numberOfPages;
    private final AvailabilityStatus availabilityStatus;
    private final AvailablePieces availablePieces;
    private final BookPrice bookPrice;

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @JsonCreator
    public static BookCommand fromJson(Json json) {
        return new BookCommand(
                JsonDecoder.field(json, "bookTitle", title ->
                        new BookTitle(JsonDecoder.string(title))),

                JsonDecoder.field(json, "bookAuthor", author ->
                        new BookAuthor(JsonDecoder.string(author))),

                JsonDecoder.field(json, "releaseDate", releaseDate ->
                        new ReleaseDate(LocalDate.parse(JsonDecoder.string(releaseDate), LOCAL_DATE_FORMATTER))),

                JsonDecoder.field(json, "numberOfPages", numberOfPages ->
                        new NumberOfPages(JsonDecoder.int_(numberOfPages))),

                JsonDecoder.field(json, "availabilityStatus", availabilityStatus ->
                        new AvailabilityStatus(JsonDecoder.boolean_(availabilityStatus))),

                JsonDecoder.field(json, "availablePieces", availablePieces ->
                        new AvailablePieces(JsonDecoder.int_(availablePieces))),

                JsonDecoder.field(json, "bookPrice", price ->
                        new BookPrice(JsonDecoder.bigDecimal(price)))
        );
    }

    @Override
    public Json toJson() {
        return Json.objectBuilder()
                .put("bookTitle", Json.of(bookTitle.getBookTitle()))
                .put("bookAuthor", Json.of(bookAuthor.getBookAuthor()))
                .put("releaseDate", Json.of(LOCAL_DATE_FORMATTER.format(releaseDate.getReleaseDate())))
                .put("numberOfPages", Json.of(numberOfPages.getNumberOfPages()))
                .put("availabilityStatus", Json.of(availabilityStatus.getAvailabilityStatus()))
                .put("availablePieces", Json.of(availablePieces.getAvailablePieces()))
                .put("bookPrice", Json.of(bookPrice.getBookPrice()))
                .build();
    }
}
