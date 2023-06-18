package com.bookstoreapplication.bookstore.delivery;

import com.bookstoreapplication.bookstore.auth.core.JwtFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@AllArgsConstructor
class DeliveryController {

    private final JwtFacade jwtFacade;
    private final DeliveryHandler deliveryHandler;

    @GetMapping("/number")
    ResponseEntity<?> getDeliveryByOrderNumber(@RequestBody UUID orderNumber){
        var jsonDelivery = DeliveryJsonQueryResponse
                .from(deliveryHandler.getDeliveryByOrderNumber(orderNumber));
        return new ResponseEntity<>(jsonDelivery, HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<?> getAllDeliveries(){
        List<DeliveryJsonQueryResponse> jsonDelivery = DeliveryJsonQueryResponse
                .from(deliveryHandler.getAllDeliveries());
        return new ResponseEntity<>(jsonDelivery, HttpStatus.OK);
    }

    @PostMapping("/send/{deliveryNumber}")
    ResponseEntity<?> markDeliveryAsSend(@PathVariable UUID deliveryNumber){
        deliveryHandler.markDeliveryAsSent(deliveryNumber);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/receive/{deliveryNumber}")
    ResponseEntity<?> markDeliveryAsReceived(@PathVariable UUID deliveryNumber){
        deliveryHandler.markDeliveryAsReceived(deliveryNumber);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
