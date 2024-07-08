package com.pia.reservation.controller;


import com.pia.reservation.dto.request.HotelSaveRequest;
import com.pia.reservation.dto.response.HotelResponse;
import com.pia.reservation.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;



    @PostMapping("/s")
    public ResponseEntity saveHotels(@RequestBody List<HotelSaveRequest> hotelSaveRequests) {
        hotelService.saveHotels(hotelSaveRequests);
        return ResponseEntity.ok("Hotels saved successfully");
    }

    @PostMapping
    public ResponseEntity saveHotel(@RequestBody  HotelSaveRequest hotelSaveRequest){
        hotelService.saveHotel(hotelSaveRequest);
        return ResponseEntity.ok("Hotel saved succesfully");
    }

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels(@RequestParam Map<String, String> params){

        return ResponseEntity.ok(hotelService.getAllHotels(params));
    }
}
