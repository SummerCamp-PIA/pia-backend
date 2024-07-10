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
    @Transactional
    public void save(ReservationSaveRequestDto reservationSaveRequestDto) {


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

        System.out.println(reservationSaveRequestDto.getRoomAmentites());
        if (room.getFirst() == null) {
            throw new NoSuchElementException("No available room found");

        }

        for (Guest guest : reservationSaveRequestDto.getGuests()) {
            guest = modelMapper.map(guest, Guest.class);
            System.out.println(guest.toString());
            guestRepository.save(guest);
        }
        Reservation reservation = new Reservation();
        System.out.println(room.size());

        reservation.setRoom(room.getFirst());
        reservation.setCheckInDate(Date.valueOf(reservationSaveRequestDto.getCheckInDate()));
        reservation.setCheckOutDate(Date .valueOf(reservationSaveRequestDto.getCheckOutDate()));
        reservationRepository.save(reservation);

        // Dto ?
        Room roomDto = modelMapper.map(room.getFirst(), Room.class);
        roomDto.setCheckInDate(Date.valueOf(reservationSaveRequestDto.getCheckInDate()));
        roomDto.setCheckOutDate(Date.valueOf(reservationSaveRequestDto.getCheckOutDate()));
        roomRepository.save(roomDto);



        for(Guest guest: reservationSaveRequestDto.getGuests()){
            guest.setReservation(reservation);
            guestRepository.save(guest);
        }

 }
}
