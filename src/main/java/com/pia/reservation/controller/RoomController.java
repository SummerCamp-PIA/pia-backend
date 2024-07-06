package com.pia.reservation.controller;

import com.pia.reservation.model.Room;
import com.pia.reservation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.saveRoom(room);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}