package com.pia.reservation.dto.request;


import com.pia.reservation.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelSaveRequest {

    private String name;
    private String hotelType;
    private String accomudationType;
    private List<String> amentities;
    private Location location;
    private List<RoomDto> rooms;

}
