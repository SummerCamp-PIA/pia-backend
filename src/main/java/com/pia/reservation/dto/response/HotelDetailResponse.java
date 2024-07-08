package com.pia.reservation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDetailResponse {

    private String name;

    private String hotelType;
    private String accomudationType;
    private String amentities;
    private Integer avgScore;

    private String email;
    private String phoneNo;
    private List<RoomDto> rooms;

}
