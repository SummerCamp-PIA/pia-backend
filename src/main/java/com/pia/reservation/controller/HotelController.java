package com.pia.reservation.controller;

import com.pia.reservation.dto.request.HotelRequestDto;
import com.pia.reservation.dto.response.HotelResponseDto;
import com.pia.reservation.model.Hotel;
import com.pia.reservation.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<HotelResponseDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelResponseDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    public Hotel createHotel(@RequestBody HotelRequestDto hotelRequestDto) {
        return hotelService.saveHotel(hotelRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
    }
}
