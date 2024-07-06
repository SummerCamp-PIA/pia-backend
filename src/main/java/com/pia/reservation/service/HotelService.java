package com.pia.reservation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pia.reservation.dto.request.HotelRequestDto;
import com.pia.reservation.dto.response.HotelResponseDto;
import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.Room;
import com.pia.reservation.repository.HotelRepository;
import com.pia.reservation.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomService roomService;

    public List<HotelResponseDto> getAllHotels() {

        List<Hotel> hotels = hotelRepository.findAll();

        List<HotelResponseDto> hotelResponseDtos = new ArrayList<>();
        for(Hotel hotel : hotels){
            hotelResponseDtos.add(ModelMapperUtil.modelMapper.map(hotel, HotelResponseDto.class));
        }

        return hotelResponseDtos;
    }

    public HotelResponseDto getHotelById(Long id) {

        Hotel hotel = hotelRepository.findById(id).orElse(null);

       return ModelMapperUtil.modelMapper.map(hotel, HotelResponseDto.class);

    }

    public Hotel saveHotel(HotelRequestDto hotelRequestDto) {

        Hotel hotel = ModelMapperUtil.modelMapper.map(hotelRequestDto,Hotel.class);

        if (hotel.getRooms() != null) {
            for (Room room : hotel.getRooms()) {
                room.setHotel(hotel);
            }
        }
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}
