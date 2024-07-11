package com.pia.reservation.service;

import com.pia.reservation.dto.request.HotelSaveRequest;
import com.pia.reservation.dto.response.HotelResponse;
import com.pia.reservation.dto.response.Offer;
import com.pia.reservation.dto.response.RoomDto;
import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.Room;
import com.pia.reservation.repository.HotelRepository;
import com.pia.reservation.repository.LocationRepository;
import com.pia.reservation.repository.RoomRepository;
import com.pia.reservation.util.FileService;
import com.pia.reservation.util.SpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FileService fileService;



    public void saveHotel(HotelSaveRequest hotelSaveRequest, MultipartFile[] multipartFile) throws IOException {
        // Map the hotelSaveRequest to a Hotel entity
        Hotel hotel = modelMapper.map(hotelSaveRequest, Hotel.class);

        // Save the hotel first to get its ID
        hotel = hotelRepository.save(hotel);

        // List to hold all room instances
        List<Room> roomInstances = new ArrayList<>();
        System.out.println(hotelSaveRequest.getRooms());
        // Iterate over the room DTOs and create multiple instances based on roomCount
        for (com.pia.reservation.dto.request.SubDto.RoomDto roomDto : hotelSaveRequest.getRooms()) {
            System.out.println("RoomDto: " + roomDto);

            for (int i = 0; i < roomDto.getTotalRoomCount(); i++) {
                Room room = modelMapper.map(roomDto, Room.class);
                room.setRoomAmentites(String.valueOf(roomDto.getRoomAmentites()));
                room.setHotel(hotel); // Set the relationship
                roomInstances.add(room);
            }
        }

        // Save all room instances
        roomRepository.saveAll(roomInstances);

        fileService.saveFiles(multipartFile,hotel);

        // Optionally update the hotel with the room references (if needed)
        hotel.setRooms(roomInstances);
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

        for (int i = 0; i <hotelResponse.size() ; i++) {
            Long hotel_id = hotels.get(i).getHotel_id();

            Integer roomPrice = roomRepository.findMinPriceByHotelId(hotel_id);
            System.out.println("Room Price: " + roomPrice);

            Integer accomudationPrice = hotels.get(i).getAccomudatipnTypePrice();
            int finalAccomudationPrice = (accomudationPrice != null) ? accomudationPrice : 0;

            System.out.println("Accommodation Price: " + finalAccomudationPrice);
            hotelResponse.get(i).setTotalPrice((int) (finalAccomudationPrice + roomPrice));
        }
        return hotelResponse;
    }

    public HotelResponse getHotelById(Long id){

        Hotel hotel = hotelRepository.findById(id).orElseThrow();


        List<RoomDto> roomDtos = new ArrayList<>();
        for(Room room : hotel.getRooms()){
            roomDtos.add(modelMapper.map(room, RoomDto.class));
        }

        HotelResponse hotelResponse = modelMapper.map(hotel, HotelResponse.class);
//        hotelResponse.setTotalPrice(roomDtos.get(1).getRoomPrice());

        return hotelResponse;

    }
    public int getOffer(Long id, String roomType){
        int totalPrice = 0;
        List<Room> room = roomRepository.findByRoomType(roomType);

        Hotel hotel = hotelRepository.findById(id).orElseThrow();
        totalPrice = hotel.getAccomudatipnTypePrice();

        totalPrice += room.getFirst().getPrice();
        return totalPrice;
    }

    public Hotel getHotel(Long id){
        return hotelRepository.findById(id).orElseThrow();
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
