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
@CrossOrigin("http://localhost:3000")
class DeliveryController {

    private final JwtFacade jwtFacade;
    private final DeliveryHandler deliveryHandler;

    @GetMapping("/number")
    ResponseEntity<?> getDeliveryByOrderNumber(HttpServletRequest request, @RequestBody UUID orderNumber){
        jwtFacade.extractUserId(request);
        //TODO request
        var jsonDelivery = DeliveryJsonQueryResponse
                .from(deliveryHandler.getDeliveryByOrderNumber(orderNumber));
        return new ResponseEntity<>(jsonDelivery, HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<?> getAllDeliveries(HttpServletRequest request){
        jwtFacade.extractUserId(request);
        //TODO request
        List<DeliveryJsonQueryResponse> jsonDelivery = DeliveryJsonQueryResponse
                .from(deliveryHandler.getAllDeliveries());
        return new ResponseEntity<>(jsonDelivery, HttpStatus.OK);
    }

    @PostMapping("/send/{deliveryId}")
    ResponseEntity<?> markDeliveryAsSend(@PathVariable long deliveryId){
        deliveryHandler.markDeliveryAsSent(deliveryId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/receive/{deliveryId}")
    ResponseEntity<?> markDeliveryAsReceived(@PathVariable long deliveryId){
        deliveryHandler.markDeliveryAsReceived(deliveryId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
