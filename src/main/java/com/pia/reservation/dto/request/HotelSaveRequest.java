package com.pia.reservation.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.pia.reservation.dto.request.SubDto.HotelSaveLocationDto;
import com.pia.reservation.dto.request.SubDto.RoomDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "Hotel save request data transfer object")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelSaveRequest {

    @Schema(description = "Hotel's name", example = "Grand Plaza")
    @JsonProperty("hotelName")
    private String name;

    @Schema(description = "Type of the hotel", example = "Resort")
    private String hotelType;

    @Schema(description = "Accommodation type", example = "All-Inclusive")
    private String accomudationType;

    @Schema(description = "List of amenities", example = "[\"WiFi\", \"Pool\", \"Gym\"]")
    private List<String> amentities;

    @Schema(description = "Location details of the hotel")
    @JsonProperty("location")
    private HotelSaveLocationDto location;

    @Schema(description = "List of room details")
    private List<RoomDto> rooms;

    @JsonProperty("starRating")
    private Integer star;

    @JsonProperty("phone")
    private String phoneNo;
    @JsonProperty("email")
    private String email;

    private int accomudatipnTypePrice;

    @JsonProperty("hotelPhotos")
    private MultipartFile [] images;
}
