package com.pia.reservation.service;

import com.pia.reservation.dto.request.HotelSaveRequest;
import com.pia.reservation.dto.response.HotelDetailResponse;
import com.pia.reservation.dto.response.HotelResponse;
import com.pia.reservation.dto.response.RoomDto;
import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.Location;
import com.pia.reservation.model.Room;
import com.pia.reservation.repository.HotelRepository;
import com.pia.reservation.repository.LocationRepository;
import com.pia.reservation.util.ModelMapperUtil;
import com.pia.reservation.util.SpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pia.reservation.util.ModelMapperUtil.modelMapper;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ReservationService reservationService;



    public void saveHotel(HotelSaveRequest hotelSaveRequest){

        Hotel hotel  = modelMapper.map(hotelSaveRequest, Hotel.class);

        for(Room room : hotel.getRooms()){
            room.setHotel(hotel);
        }

        hotelRepository.save(hotel);
    }

    public void saveHotels(List<HotelSaveRequest> hotelSaveRequests) {
        for (HotelSaveRequest hotelSaveRequest : hotelSaveRequests) {
            Hotel hotel = modelMapper.map(hotelSaveRequest, Hotel.class);

            for (Room room : hotel.getRooms()) {
                room.setHotel(hotel);
            }

            hotelRepository.save(hotel);
        }
    }

    public List<HotelResponse> getAllHotels(Map<String,String> params){
        SpecificationBuilder<Hotel> builder = new SpecificationBuilder<>();
        Specification<Hotel> spec = builder.build(params);

        List<Hotel> hotels = hotelRepository.findAll(spec);
        List<HotelResponse> hotelResponse = hotels.stream()
                .map(hotel -> modelMapper.map(hotel, HotelResponse.class))
                .collect(Collectors.toList());
        return hotelResponse;
    }

    /*public HotelDetailResponse getHotel(Long hotelId, Date startDate, Date endDate){
       Hotel hotel =  hotelRepository.findById(hotelId).orElseThrow();

       HotelDetailResponse hotelDetailResponse = modelMapper.map(hotel,HotelDetailResponse.class);


       for(Room room : hotel.getRooms()){

           RoomDto.builder()
                   .roomType(room.getRoomType())
                   .roomPrice(room.getPrice())
                   .roomCount()
                   .build()
       }
       hotelDetailResponse.setRooms();
    } */




}
