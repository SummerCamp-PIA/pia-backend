package com.pia.reservation.service;


import com.pia.reservation.dto.request.ReservationSaveRequestDto;
import com.pia.reservation.model.Guest;
import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.Reservation;
import com.pia.reservation.model.Room;
import com.pia.reservation.repository.GuestRepository;
import com.pia.reservation.repository.HotelRepository;
import com.pia.reservation.repository.ReservationRepository;
import com.pia.reservation.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public Room findAvailableRooms(ReservationSaveRequestDto reservationSaveRequestDto) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(reservationSaveRequestDto.getHotel_id());
        if (!hotelOptional.isPresent()) {
            throw new RuntimeException("Hotel not found");
        }
        Hotel hotel = hotelOptional.get();
        List<Room> room = roomRepository.findAvailableRoom(
                hotel.getHotel_id(),
                reservationSaveRequestDto.getRoomType(),
                reservationSaveRequestDto.getRoomAmentites(),
                Date.valueOf(reservationSaveRequestDto.getCheckInDate()),
                Date.valueOf(reservationSaveRequestDto.getCheckOutDate())
        );
        if (room.getFirst() == null) {
            throw new NoSuchElementException("No available room found");

        }
        return room.getFirst();
    }
    public void saveGuest(ReservationSaveRequestDto reservationSaveRequestDto) {
        for (Guest guest : reservationSaveRequestDto.getGuests()) {
            guest = modelMapper.map(guest, Guest.class);
            System.out.println(guest.toString());
            System.out.println(guest.getReservation());
            guestRepository.save(guest);
        }

    }
    public  Reservation saveReservation(Room room,ReservationSaveRequestDto reservationSaveRequestDto) {
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCheckInDate(Date.valueOf(reservationSaveRequestDto.getCheckInDate()));
        reservation.setCheckOutDate(Date.valueOf(reservationSaveRequestDto.getCheckOutDate()));
        reservationRepository.save(reservation);
        return reservation;
    }
    public void saveRoomByreservation(Room room,ReservationSaveRequestDto reservationSaveRequestDto){
        room.setCheckInDate(Date.valueOf(reservationSaveRequestDto.getCheckInDate()));
        room.setCheckOutDate(Date.valueOf(reservationSaveRequestDto.getCheckOutDate()));
        roomRepository.save(room);

    }
    @Transactional
    public void save(ReservationSaveRequestDto reservationSaveRequestDto) {
        Room availableRoom = findAvailableRooms(reservationSaveRequestDto);
        saveGuest(reservationSaveRequestDto);
        Reservation reservation = saveReservation(availableRoom,reservationSaveRequestDto);
        saveRoomByreservation(availableRoom,reservationSaveRequestDto);
        for(Guest guest: reservationSaveRequestDto.getGuests()){
            guest.setReservation(reservation);
            guestRepository.save(guest);
       }







//        Optional<Hotel> hotelOptional = hotelRepository.findById(reservationSaveRequestDto.getHotel_id());
//        if (!hotelOptional.isPresent()) {
//            throw new RuntimeException("Hotel not found");
//        }
//        Hotel hotel = hotelOptional.get();
//        List<Room> room = roomRepository.findAvailableRoom(
//                hotel.getHotel_id(),
//                reservationSaveRequestDto.getRoomType(),
//                reservationSaveRequestDto.getRoomAmentites(),
//                Date.valueOf(reservationSaveRequestDto.getCheckInDate()),
//                Date.valueOf(reservationSaveRequestDto.getCheckOutDate())
//        );
//
//        System.out.println(reservationSaveRequestDto.getRoomAmentites());
//        if (room.getFirst() == null) {
//            throw new NoSuchElementException("No available room found");
//
//        }
//
//        for (Guest guest : reservationSaveRequestDto.getGuests()) {
//            guest = modelMapper.map(guest, Guest.class);
//            System.out.println(guest.toString());
//            System.out.println(guest.getReservation());
//            guestRepository.save(guest);
//        }
//        Reservation reservation = new Reservation();
//        System.out.println(room.size());
//
//        reservation.setRoom(room.getFirst());
//        reservation.setCheckInDate(Date.valueOf(reservationSaveRequestDto.getCheckInDate()));
//        reservation.setCheckOutDate(Date .valueOf(reservationSaveRequestDto.getCheckOutDate()));
//        reservationRepository.save(reservation);
//
//       Room savedRoom = room.getFirst();
//       savedRoom.setCheckInDate(Date.valueOf(reservationSaveRequestDto.getCheckInDate()));
//        savedRoom.setCheckOutDate(Date.valueOf(reservationSaveRequestDto.getCheckOutDate()));
//        roomRepository.save(savedRoom);
//
//
//
//        for(Guest guest: reservationSaveRequestDto.getGuests()){
//            guest.setReservation(reservation);
//            guestRepository.save(guest);
//        }

 }
}
