package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.auth.JwtService;
import dev.mccue.json.Json;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class CartController {

    private final CartHandler cartHandler;
    private final JwtService jwtService;

    @PostMapping("")
    ResponseEntity<?> initializeCart(@RequestBody Json json, HttpServletRequest request){
        long bookId = JsonBookId.fromJson(json);
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.initializeCart(customerId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    ResponseEntity<?> getCart(HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        return new ResponseEntity<>(cartHandler.getCart(customerId), HttpStatus.OK);
    }

    @PostMapping("/product")
    ResponseEntity<?> addProduct(@RequestBody Json json, HttpServletRequest request){
        long bookId = JsonBookId.fromJson(json);
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.addProduct(customerId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/product")
    ResponseEntity<?> deleteProduct(@RequestBody Json json, HttpServletRequest request){
        long bookId = JsonBookId.fromJson(json);
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.deleteProduct(customerId, bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/product/increase")
    ResponseEntity<?> increaseProductAmount(@RequestBody Json json, HttpServletRequest request){
        long bookId = JsonBookId.fromJson(json);
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.increaseProductAmount(customerId, bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/product/decrease")
    ResponseEntity<?> decreaseProductAmount(@RequestBody Json json, HttpServletRequest request){
        long bookId = JsonBookId.fromJson(json);
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.decreaseProductAmount(customerId, bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
