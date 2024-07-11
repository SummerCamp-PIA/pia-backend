package com.pia.reservation.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Hotel response data transfer object")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {

    @Schema(description = "Hotel's id", example = "1")
    private Integer hotel_id;

    @Schema(description = "Hotel's name", example = "Grand Plaza")
    private String name;

    @Schema(description = "Type of the hotel", example = "Resort")
    private String hotelType;

    @Schema(description = "Accommodation type", example = "All-Inclusive")
    private String accomudationType;

    @Schema(description = "List of amenities as a single string", example = "WiFi, Pool, Gym")
    private String amentities;

    @Schema(description = "Average score of the hotel", example = "4")
    private Integer avgScore;

    private Integer totalPrice;

}
