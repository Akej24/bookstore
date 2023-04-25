package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

class BookCommandDeserializer extends JsonDeserializer<BookCommand> {

    @Override
    public BookCommand deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        Title title = new Title(node.get("title").asText());
        Author author = new Author(node.get("author").asText());
        ReleaseDate releaseDate = jp.getCodec().treeToValue(node.get("releaseDate"), ReleaseDate.class);
        NumberOfPages numberOfPages = new NumberOfPages(node.get("numberOfPages").asInt());
        AvailabilityStatus availabilityStatus = new AvailabilityStatus(node.get("availabilityStatus").asBoolean());
        AvailablePieces availablePieces = new AvailablePieces(node.get("availablePieces").asInt());
        Price price = jp.getCodec().treeToValue(node.get("price"), Price.class);

        return new BookCommand(title, author, releaseDate, numberOfPages, availabilityStatus, availablePieces, price);
    }

}