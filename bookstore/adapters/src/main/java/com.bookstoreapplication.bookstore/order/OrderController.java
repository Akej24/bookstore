package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.auth.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class OrderController {

    private final OrderHandler orderHandler;
    private final JwtService jwtService;

    @GetMapping("")
    ResponseEntity<?> getCustomerOrders(HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        return new ResponseEntity<>(orderHandler.getCustomerOrders(customerId), HttpStatus.OK);
    }
}
