package com.pia.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pia.reservation.dto.request.PaymentRequest;
import com.pia.reservation.dto.request.SubDto.CreditCardDto;
import com.pia.reservation.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreditCardDto creditCardDto;
    private PaymentRequest paymentRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        creditCardDto = CreditCardDto.builder()
                .ownerName("John Doe")
                .cardNo("1234567890123456")
                .expDate("12/2025")
                .cvv("123")
                .build();

        paymentRequest = new PaymentRequest();
        paymentRequest.setCreditCardDto(creditCardDto);
        paymentRequest.setHotelName("Test Hotel");
        paymentRequest.setPrice(1000);
    }

    @Test
    public void testSaveDummyCards() throws Exception {
        when(paymentService.saveDummyCards(any(List.class))).thenReturn("Dummy cards has been saved successfully");

        mockMvc.perform(post("/payment/dummy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(creditCardDto))))
                .andExpect(status().isOk())
                .andExpect(content().string("Dummy cards has been saved successfully"));
    }

    @Test
    public void testMakePayment() throws Exception {
        when(paymentService.makePayment(any(PaymentRequest.class))).thenReturn(true);

        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment success"));

        when(paymentService.makePayment(any(PaymentRequest.class))).thenReturn(false);

        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Payment rejected"));
    }
}
