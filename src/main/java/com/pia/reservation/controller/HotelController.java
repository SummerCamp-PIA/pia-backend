package com.pia.reservation.controller;


import com.pia.reservation.dto.request.HotelSaveRequest;
import com.pia.reservation.dto.response.HotelResponse;
import com.pia.reservation.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Operation(summary = "Save multiple hotels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotels saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/s")
    public ResponseEntity<String> saveHotels(@RequestBody List<HotelSaveRequest> hotelSaveRequests) {
        hotelService.saveHotels(hotelSaveRequests);
        return ResponseEntity.ok("Hotels saved successfully");
    }

    @Operation(summary = "Save a hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<String> saveHotel(@RequestBody HotelSaveRequest hotelSaveRequest,@RequestParam("hotelPhotos") MultipartFile[] multipartFiles) throws IOException {
        hotelService.saveHotel(hotelSaveRequest,multipartFiles);
        return ResponseEntity.ok("Hotel saved successfully");
    }

    @Operation(summary = "Get all hotels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of hotels returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(hotelService.getAllHotels(params));
    }

    @Operation(summary = "Get a hotel by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel returned successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotel(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }
    @GetMapping("/offer")
    public int getOffer(@RequestParam Long id, String roomType){
        return hotelService.getOffer(id, roomType);
    }
}
