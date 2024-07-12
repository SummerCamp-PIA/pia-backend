package com.pia.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pia.reservation.dto.request.ReservationSaveRequest;
import com.pia.reservation.model.Guest;
import com.pia.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReservationSaveRequest reservationSaveRequest;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = sdf.parse("1980-01-01");

        Guest guest = new Guest();
        guest.setName("John");
        guest.setSurname("Doe");
        guest.setBirthDate(birthDate);
        guest.setIdentityNo("12345678901");

        reservationSaveRequest = new ReservationSaveRequest();
        reservationSaveRequest.setHotel_id(1L);
        reservationSaveRequest.setGuests(Arrays.asList(guest));
        reservationSaveRequest.setCheckInDate("2024-07-15");
        reservationSaveRequest.setCheckOutDate("2024-07-20");
        reservationSaveRequest.setRoomType("Single");
        reservationSaveRequest.setRoomAmentites("WiFi");
        reservationSaveRequest.setAccomudationType("All-Inclusive");
    }

    @Test
    public void testCreateReservation() throws Exception {
        doNothing().when(reservationService).save(any(ReservationSaveRequest.class));

        mockMvc.perform(post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationSaveRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation Created"));
    }
}
