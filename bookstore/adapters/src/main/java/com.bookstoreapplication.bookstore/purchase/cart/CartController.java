package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.auth.core.JwtFacade;
import dev.mccue.json.Json;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
class CartController {

    private final CartHandler cartHandler;
    private final JwtFacade jwtFacade;

    @GetMapping("")
    ResponseEntity<?> getCart(HttpServletRequest request){
        long customerId = jwtFacade.extractUserId(request);
        CartJsonQueryResponse jsonCart = CartJsonQueryResponse.from(cartHandler.getCart(customerId));
        return new ResponseEntity<>(jsonCart, HttpStatus.OK);
    }

    @PostMapping("/product")
    ResponseEntity<?> addProduct(@RequestBody Json json, HttpServletRequest request){
        long bookId = BookIdJsonCommand.fromJson(json);
        long customerId = jwtFacade.extractUserId(request);
        cartHandler.addProduct(customerId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/product/{productId}")
    ResponseEntity<?> deleteProduct(@PathVariable long productId, HttpServletRequest request){
        long customerId = jwtFacade.extractUserId(request);
        cartHandler.deleteProduct(customerId, productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/product/increase")
    ResponseEntity<?> increaseProductAmount(@RequestBody Json json, HttpServletRequest request){
        long bookId = BookIdJsonCommand.fromJson(json);
        long customerId = jwtFacade.extractUserId(request);
        cartHandler.increaseProductAmount(customerId, bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/product/decrease")
    ResponseEntity<?> decreaseProductAmount(@RequestBody Json json, HttpServletRequest request){
        long bookId = BookIdJsonCommand.fromJson(json);
        long customerId = jwtFacade.extractUserId(request);
        cartHandler.decreaseProductAmount(customerId, bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
