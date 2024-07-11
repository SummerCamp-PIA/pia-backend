package com.pia.reservation.controller;

import com.pia.reservation.dto.request.ReservationSaveRequest;
import com.pia.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservation")
public class ReservationController {
    @Autowired
    ReservationService reservationService;
    @PostMapping
    public ResponseEntity<String>  createReservation(@RequestBody ReservationSaveRequest reservation) {

         reservationService.save(reservation);
        return  ResponseEntity.ok("Reservation Created");
    }
}
