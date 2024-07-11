package com.pia.reservation.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private Integer amount;
    private String userName;
    @JsonProperty("credit")
    private String creditCardNo;
}
