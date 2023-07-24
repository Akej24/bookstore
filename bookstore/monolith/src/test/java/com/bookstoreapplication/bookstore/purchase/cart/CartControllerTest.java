package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.auth.core.JwtFacade;
import com.bookstoreapplication.bookstore.book.value_object.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles(profiles = "test")
@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(com.bookstoreapplication.bookstore.config.JsonModuleConfig.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private BookProductRepository bookProductRepository;
    @MockBean
    private JwtFacade jwtFacade;
    @MockBean
    private CartHandler cartHandler;

    @Test
    @DisplayName("Should pass when successively added product to existing cart")
    void addProduct() throws Exception {
        when(jwtFacade.extractUserId(any()))
                .thenReturn(10L);
        when(cartRepository.existsByCustomerId(anyLong()))
                .thenReturn(true);
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
        mockMvc.perform(post("/api/v1/cart/product")
                .header("Content-Type", "application/json")
                .content("{\"bookId\":14}"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}