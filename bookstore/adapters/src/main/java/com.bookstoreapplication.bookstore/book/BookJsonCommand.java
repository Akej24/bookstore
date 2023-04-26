package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import dev.mccue.json.Json;
import dev.mccue.json.JsonDecoder;
import dev.mccue.json.JsonEncodable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
class BookJsonCommand implements JsonEncodable {

    private Title title;
    private Author author;
    private ReleaseDate releaseDate;
    private NumberOfPages numberOfPages;
    private AvailabilityStatus availabilityStatus;
    private AvailablePieces availablePieces;
    private Price price;

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @JsonCreator
    public static BookCommand fromJson(Json json) {
        return new BookCommand(
                JsonDecoder.field(json, "title", title ->
                        new Title(JsonDecoder.string(title))),

                JsonDecoder.field(json, "author", author ->
                        new Author(JsonDecoder.string(author))),

                JsonDecoder.field(json, "releaseDate", releaseDate ->
                        new ReleaseDate(LocalDate.parse(JsonDecoder.string(releaseDate), LOCAL_DATE_FORMATTER))),

                JsonDecoder.field(json, "numberOfPages", numberOfPages ->
                        new NumberOfPages(JsonDecoder.int_(numberOfPages))),

                JsonDecoder.field(json, "availabilityStatus", availabilityStatus ->
                        new AvailabilityStatus(JsonDecoder.boolean_(availabilityStatus))),

                JsonDecoder.field(json, "availablePieces", availablePieces ->
                        new AvailablePieces(JsonDecoder.int_(availablePieces))),

                JsonDecoder.field(json, "price", price ->
                        new Price(JsonDecoder.bigDecimal(price)))
        );
    }

    @Override
    public Json toJson() {
        return Json.objectBuilder()
                .put("title", Json.of(title.getTitle()))
                .put("author", Json.of(author.getAuthor()))
                .put("releaseDate", Json.of(LOCAL_DATE_FORMATTER.format(releaseDate.getReleaseDate())))
                .put("numberOfPages", Json.of(numberOfPages.getNumberOfPages()))
                .put("availabilityStatus", Json.of(availabilityStatus.getAvailabilityStatus()))
                .put("availablePieces", Json.of(availablePieces.getAvailablePieces()))
                .put("price", Json.of(price.getPrice()))
                .build();
    }
}
