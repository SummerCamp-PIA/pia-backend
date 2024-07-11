package com.pia.reservation.dto.request.SubDto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Room data transfer object for hotel save request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    @Schema(description = "Type of the room", example = "Deluxe")
    private String roomType;

    @Schema(description = "Price of the room per night", example = "150")
    private Integer price;

    @Schema(description = "Total number of rooms of this type", example = "10")
    private Integer totalRoomCount;

    private Integer bedNo;

    private List<String> roomAmentites;

    private Integer maxOccupancy;
}
