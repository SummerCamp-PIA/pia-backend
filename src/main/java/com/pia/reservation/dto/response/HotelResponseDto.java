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
public class HotelResponseDto {

    private String name;
    private LocationReponseDto location;

}
