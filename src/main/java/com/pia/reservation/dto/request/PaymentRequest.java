package com.pia.reservation.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.pia.reservation.dto.request.SubDto.CreditCardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {


    @JsonProperty("paymentDetails")
    private CreditCardDto creditCardDto;
    private Integer price;
    private String hotelName;




}
