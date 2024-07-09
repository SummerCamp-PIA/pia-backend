package com.pia.reservation.dto.request.SubDto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDto {

    private String ownerName;
    private String cardNo;
    @JsonFormat(pattern = "MM/YYYY")
    private String expDate;
    private String cvv;


}
