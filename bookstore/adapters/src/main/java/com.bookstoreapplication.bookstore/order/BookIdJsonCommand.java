package com.bookstoreapplication.bookstore.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.mccue.json.Json;
import dev.mccue.json.JsonDecoder;
import dev.mccue.json.JsonEncodable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class BookIdJsonCommand implements JsonEncodable {

    private long bookId;

    @JsonCreator
    public static long fromJson(Json json) {
        return JsonDecoder.field(json, "bookId", JsonDecoder::long_);
    }

    @Override
    public Json toJson() {
        return Json.objectBuilder()
                .put("bookId", Json.of(bookId))
                .build();
    }

}
