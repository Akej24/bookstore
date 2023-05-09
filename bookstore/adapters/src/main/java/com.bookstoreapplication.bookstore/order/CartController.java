package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.auth.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
@Validated
@CrossOrigin("http://localhost:3000")
class CartController {

    private final CartHandler cartHandler;
    private final JwtService jwtService;

    @PostMapping("")
    ResponseEntity<?> initializeCart(@RequestBody long bookId, HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.initializeCart(customerId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/product")
    ResponseEntity<?> addProduct(@RequestBody long bookId, HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.addProduct(customerId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/product")
    ResponseEntity<?> deleteProduct(@RequestBody long bookId, HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.deleteProduct(customerId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/product/amount/increase")
    ResponseEntity<?> increaseProductAmount(@RequestBody long bookId, HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.increaseProductAmount(customerId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/product/amount/decrease")
    ResponseEntity<?> decreaseProductAmount(@RequestBody long bookId, HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.decreaseProductAmount(customerId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/checkout")
    ResponseEntity<?> checkout(HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.checkout(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
