package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.purchase.order.OrderDetail;
import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class BooksDecrementJsonConverter {

    private final ObjectMapper objectMapper;

    public String toJson(List<OrderDetail> orderDetails) {
        try {
            ObjectNode rootNode = objectMapper.createObjectNode();
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for (OrderDetail orderDetail : orderDetails) {
                ObjectNode bookWithAmountNode = objectMapper.createObjectNode();
                bookWithAmountNode.put("bookId", orderDetail.getBookId());
                bookWithAmountNode.put("amount", orderDetail.getBooksAmount().getBooksAmount());
                arrayNode.add(bookWithAmountNode);
            }
            rootNode.set("booksToDecrement", arrayNode);
            return objectMapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to write to json books decrement message");
        }
    }

    public List<BooksDecrementCommand> fromJson(String json){
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            List<BooksDecrementCommand> booksToDecrement = new ArrayList<>();
            ArrayNode booksToDecrementNode = (ArrayNode) rootNode.get("booksToDecrement");
            for (JsonNode bookToDecrementNode : booksToDecrementNode) {
                long bookId = bookToDecrementNode.get("bookId").asLong();
                Integer amount = bookToDecrementNode.get("amount").asInt();
                booksToDecrement.add(new BooksDecrementCommand(bookId, new BooksAmount(amount)));
            }
            return booksToDecrement;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to write from json books decrement message");
        }
    }
}
