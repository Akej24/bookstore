package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.auth.core.JwtFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
class OrderController {

    private final OrderHandler orderHandler;
    private final JwtFacade jwtFacade;

    @PostMapping("")
    ResponseEntity<?> order(HttpServletRequest request){
        long customerId = jwtFacade.extractUserId(request);
        orderHandler.order(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    ResponseEntity<?> getCustomerOrders(HttpServletRequest request){
        long customerId = jwtFacade.extractUserId(request);
        List<OrderJsonQueryResponse> jsonOrders = OrderJsonQueryResponse.from(orderHandler.getCustomerOrders(customerId));
        return new ResponseEntity<>(jsonOrders, HttpStatus.OK);
    }
}
