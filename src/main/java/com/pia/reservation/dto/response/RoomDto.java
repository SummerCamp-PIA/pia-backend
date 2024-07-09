package com.pia.reservation.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Room data transfer object for hotel response")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    @Schema(description = "Type of the room", example = "Deluxe")
    private String roomType;

    @Schema(description = "Number of rooms of this type", example = "10")
    private Integer totalRoomCount;

    @Schema(description = "Price of the room per night", example = "150")
    private Integer roomPrice;

    private Integer bedNo;

    private String roomAmentites;

    private Integer maxOccupancy;
}