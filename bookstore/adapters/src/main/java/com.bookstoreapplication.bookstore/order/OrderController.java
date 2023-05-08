package com.bookstoreapplication.bookstore.order;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchases")
@AllArgsConstructor
@Validated
@CrossOrigin("http://localhost:3000")
class OrderController {

    private final CartHandler cartHandler;



}
