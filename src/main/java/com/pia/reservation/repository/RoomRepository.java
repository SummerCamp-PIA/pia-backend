package com.pia.reservation.repository;

import com.pia.reservation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoomRepository  extends JpaRepository<Room,Long> {
@Query("SELECT r FROM Room r  " +
        "WHERE r.hotel.hotel_id = :hotel_id AND r.roomType = :roomType AND r.roomAmentites LIKE %:roomAmentites% " +
        "AND NOT EXISTS " +
        "(SELECT res FROM Reservation res WHERE res.room = r AND ((res.checkInDate BETWEEN :checkInDate AND :checkOutDate)))")
List<Room> findAvailableRoom(@Param("hotel_id") Long hotelId,
                             @Param("roomType") String roomType,
                             @Param("roomAmentites") String roomAmentites,
                             @Param("checkInDate") Date checkInDate,
                             @Param("checkOutDate") Date checkOutDate
                           );
    @Query("SELECT min(r.price) FROM Room r WHERE r.hotel.hotel_id = :hotelId")
    Integer findMinPriceByHotelId(@Param("hotelId") Long hotelId);
   List<Room>  findByRoomType(String roomType);

}
