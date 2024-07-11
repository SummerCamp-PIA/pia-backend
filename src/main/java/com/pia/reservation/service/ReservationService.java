package com.pia.reservation.service;


import com.pia.reservation.dto.request.ReservationSaveRequest;
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

    public Room findAvailableRooms(ReservationSaveRequest reservationSaveRequest) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(reservationSaveRequest.getHotel_id());
        if (!hotelOptional.isPresent()) {
            throw new RuntimeException("Hotel not found");
        }
        Hotel hotel = hotelOptional.get();
        List<Room> room = roomRepository.findAvailableRoom(
                hotel.getHotel_id(),
                reservationSaveRequest.getRoomType(),
                reservationSaveRequest.getRoomAmentites(),
                Date.valueOf(reservationSaveRequest.getCheckInDate()),
                Date.valueOf(reservationSaveRequest.getCheckOutDate())
        );
        if (room.getFirst() == null) {
            throw new NoSuchElementException("No available room found");

        }
        return room.getFirst();
    }
    public void saveGuest(ReservationSaveRequest reservationSaveRequest) {
        for (Guest guest : reservationSaveRequest.getGuests()) {
            guest = modelMapper.map(guest, Guest.class);
            System.out.println(guest.toString());
            System.out.println(guest.getReservation());
            guestRepository.save(guest);
        }

    }
    public  Reservation saveReservation(Room room, ReservationSaveRequest reservationSaveRequest) {
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCheckInDate(Date.valueOf(reservationSaveRequest.getCheckInDate()));
        reservation.setCheckOutDate(Date.valueOf(reservationSaveRequest.getCheckOutDate()));
        reservationRepository.save(reservation);
        return reservation;
    }
    public void saveRoomByreservation(Room room, ReservationSaveRequest reservationSaveRequest){
        room.setCheckInDate(Date.valueOf(reservationSaveRequest.getCheckInDate()));
        room.setCheckOutDate(Date.valueOf(reservationSaveRequest.getCheckOutDate()));
        roomRepository.save(room);

    }
    @Transactional
    public void save(ReservationSaveRequest reservationSaveRequest) {
        Room availableRoom = findAvailableRooms(reservationSaveRequest);
        saveGuest(reservationSaveRequest);
        Reservation reservation = saveReservation(availableRoom, reservationSaveRequest);
        saveRoomByreservation(availableRoom, reservationSaveRequest);
        for(Guest guest: reservationSaveRequest.getGuests()){
            guest.setReservation(reservation);
            guestRepository.save(guest);
       }







//        Optional<Hotel> hotelOptional = hotelRepository.findById(reservationSaveRequest.getHotel_id());
//        if (!hotelOptional.isPresent()) {
//            throw new RuntimeException("Hotel not found");
//        }
//        Hotel hotel = hotelOptional.get();
//        List<Room> room = roomRepository.findAvailableRoom(
//                hotel.getHotel_id(),
//                reservationSaveRequest.getRoomType(),
//                reservationSaveRequest.getRoomAmentites(),
//                Date.valueOf(reservationSaveRequest.getCheckInDate()),
//                Date.valueOf(reservationSaveRequest.getCheckOutDate())
//        );
//
//        System.out.println(reservationSaveRequest.getRoomAmentites());
//        if (room.getFirst() == null) {
//            throw new NoSuchElementException("No available room found");
//
//        }
//
//        for (Guest guest : reservationSaveRequest.getGuests()) {
//            guest = modelMapper.map(guest, Guest.class);
//            System.out.println(guest.toString());
//            System.out.println(guest.getReservation());
//            guestRepository.save(guest);
//        }
//        Reservation reservation = new Reservation();
//        System.out.println(room.size());
//
//        reservation.setRoom(room.getFirst());
//        reservation.setCheckInDate(Date.valueOf(reservationSaveRequest.getCheckInDate()));
//        reservation.setCheckOutDate(Date .valueOf(reservationSaveRequest.getCheckOutDate()));
//        reservationRepository.save(reservation);
//
//       Room savedRoom = room.getFirst();
//       savedRoom.setCheckInDate(Date.valueOf(reservationSaveRequest.getCheckInDate()));
//        savedRoom.setCheckOutDate(Date.valueOf(reservationSaveRequest.getCheckOutDate()));
//        roomRepository.save(savedRoom);
//
//
//
//        for(Guest guest: reservationSaveRequest.getGuests()){
//            guest.setReservation(reservation);
//            guestRepository.save(guest);
//        }

 }
}
