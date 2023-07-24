package com.bookstoreapplication.bookstore.auth.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles(profiles = {"integration", "test"})
@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(com.bookstoreapplication.bookstore.config.JsonModuleConfig.class)
class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LoginHandler loginHandler;

    @Test
    @DisplayName("Should pass when successively logged in and returned jwt in header")
    void addProduct() throws Exception {
        when(loginHandler.loginUser(any()))
                .thenReturn("generated-jwt-value");

        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test123@gmail.com\",\"password\":\"Abcdef1!\"}"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.header().string("Authorization", "Bearer generated-jwt-value"));
    }
}