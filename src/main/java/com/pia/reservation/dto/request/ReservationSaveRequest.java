package com.pia.reservation.dto.request;

import com.pia.reservation.model.Guest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationSaveRequest {
    private Long hotel_id;
    private List<Guest> guests;
    private String checkInDate;
    private String checkOutDate;
    private String roomType;
    private String roomAmentites;
    private String accomudationType;
}
