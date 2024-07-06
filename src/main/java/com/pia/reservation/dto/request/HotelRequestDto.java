package com.pia.reservation.dto.request;


import com.pia.reservation.model.Location;
import com.pia.reservation.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class HotelRequestDto {

    private String name;
    private Location location;
    private List<Room> rooms;
}
