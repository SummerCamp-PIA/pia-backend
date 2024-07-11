package com.pia.reservation.dto.request.SubDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelSaveLocationDto {

    private String city;
    @JsonProperty("district")
    private String state;
    private String country;

    private String address;
}
