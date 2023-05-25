package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.purchase.value_object.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonCreator;
import dev.mccue.json.Json;
import dev.mccue.json.JsonDecoder;
import dev.mccue.json.JsonEncodable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class PaymentMethodJsonCommand implements JsonEncodable {

    private PaymentMethod paymentMethod;

    @JsonCreator
    public static PaymentMethod fromJson(Json json) {
        String paymentMethod = JsonDecoder.field(json, "paymentMethod", JsonDecoder::string);
        return PaymentMethod.valueOf(paymentMethod);
    }

    @Override
    public Json toJson() {
        return Json.objectBuilder()
                .put("paymentMethod", Json.of(paymentMethod.toString()))
                .build();
    }

}
