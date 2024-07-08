package com.pia.reservation.dto.request;


import com.pia.reservation.model.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Hotel save request data transfer object")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelSaveRequest {

    @Schema(description = "Hotel's name", example = "Grand Plaza")
    private String name;

    @Schema(description = "Type of the hotel", example = "Resort")
    private String hotelType;

    @Schema(description = "Accommodation type", example = "All-Inclusive")
    private String accomudationType;

    @Schema(description = "List of amenities", example = "[\"WiFi\", \"Pool\", \"Gym\"]")
    private List<String> amentities;

    @Schema(description = "Location details of the hotel")
    private Location location;

    @Schema(description = "List of room details")
    private List<RoomDto> rooms;
}
