package com.pia.reservation.controller;


import com.pia.reservation.dto.request.PaymentRequest;
import com.pia.reservation.dto.request.SubDto.CreditCardDto;
import com.pia.reservation.dto.response.PaymentResponse;
import com.pia.reservation.service.PaymentService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/dummy")
    public ResponseEntity saveDummyCards(@RequestBody List<CreditCardDto> dummyCards){
        return ResponseEntity.ok(paymentService.saveDummyCards(dummyCards));
    }

    @PostMapping
    public ResponseEntity makePayment(@RequestBody PaymentRequest paymentRequest) throws MessagingException {

        Boolean result = paymentService.makePayment(paymentRequest);

        if(result)return ResponseEntity.ok("Payment success");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment rejected");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id){
       return ResponseEntity.ok(paymentService.getPaymentById(id));
    }


}
