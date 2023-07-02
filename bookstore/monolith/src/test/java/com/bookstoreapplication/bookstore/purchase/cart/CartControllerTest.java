package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.book.value_object.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private BookProductRepository bookProductRepository;

    @BeforeEach
    void setUp() throws Exception {
//        headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        String authHeader = mockMvc.perform(post("/api/v1/login")
//                        .content()
//                .andReturn()
//                .getResponse()
//                .getHeader("Authorization");
    }

    @Test
    void addProduct() {
        when(cartRepository.findByCustomerId(anyLong()))
                .thenReturn(Optional.of(new Cart(
                    10, new BookProduct(
                            4,
                        new BookTitle("TestTitle1"),
                        new BookAuthor("TestAuthor1"),
                        new AvailabilityStatus(true),
                        new AvailablePieces(54),
                        new BookPrice(BigDecimal.valueOf(45.23))
                ))));
        when(bookProductRepository.findById(anyLong()))
                .thenReturn(Optional.of(new BookProduct(
                        14,
                        new BookTitle("TestTitle2"),
                        new BookAuthor("TestAuthor2"),
                        new AvailabilityStatus(true),
                        new AvailablePieces(43),
                        new BookPrice(BigDecimal.valueOf(83.74))
                )));
//        mockMvc.perform()
    }
}