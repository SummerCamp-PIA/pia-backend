package com.pia.reservation.repository;

import com.pia.reservation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository  extends JpaRepository<Room,Long> {
}
