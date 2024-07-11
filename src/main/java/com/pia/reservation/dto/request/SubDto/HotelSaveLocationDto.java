package com.pia.reservation.dto.request.SubDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HotelSaveLocationDto {
    private String country;
    private String city;
    private String state;
    private String Address;
}
