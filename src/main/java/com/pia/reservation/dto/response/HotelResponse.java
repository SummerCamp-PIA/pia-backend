package com.pia.reservation.dto.response;


import com.pia.reservation.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {

    private String name;

    private String hotelType;
    private String accomudationType;
    private String amentities;
    private Integer avgScore;

   // private Offer offer;
}
