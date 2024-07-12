package com.pia.reservation.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pia.reservation.dto.request.HotelSaveRequest;
import com.pia.reservation.dto.request.SubDto.RoomDto; // Ensure this is the correct package
import com.pia.reservation.dto.request.SubDto.HotelSaveLocationDto;
import com.pia.reservation.dto.response.HotelResponse;
import com.pia.reservation.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HotelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    private HotelSaveRequest hotelSaveRequest;
    private HotelResponse hotelResponse;

    @BeforeEach
    public void setup() {
        HotelSaveLocationDto location = new HotelSaveLocationDto();
        location.setCity("City A");
        location.setState("State A");
        location.setCountry("Country A");
        location.setAddress("123 Main St");

        RoomDto roomDto = new RoomDto();
        roomDto.setRoomType("Single");
        roomDto.setPrice(100);
        roomDto.setTotalRoomCount(10);
        roomDto.setBedNo(2);
        roomDto.setRoomAmentites(Collections.singletonList("WiFi"));
        roomDto.setMaxOccupancy(2);

        hotelSaveRequest = HotelSaveRequest.builder()
                .name("Grand Plaza")
                .hotelType("Resort")
                .accomudationType("All-Inclusive")
                .amentities(Collections.singletonList("WiFi"))
                .location(location)
                .rooms(Collections.singletonList(roomDto))
                .star(5)
                .phoneNo("123-456-7890")
                .email("contact@grandplaza.com")
                .accomudatipnTypePrice(200)
                .build();

        hotelResponse = HotelResponse.builder()
                .hotel_id(1)
                .name("Grand Plaza")
                .hotelType("Resort")
                .accomudationType("All-Inclusive")
                .amentities("WiFi, Pool, Gym")
                .avgScore(4)
                .totalPrice(300)
                .build();
    }

    @Test
    public void testSaveHotels() throws Exception {
        // Mocking the service call to do nothing
        doNothing().when(hotelService).saveHotels(Collections.singletonList(hotelSaveRequest));

        mockMvc.perform(post("/hotel/s")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonList(hotelSaveRequest))))
                .andExpect(status().isOk())
                .andExpect(content().string("Hotels saved successfully"));
    }

    @Test
    public void testSaveHotel() throws Exception {
        // Mocking the service call to do nothing

        mockMvc.perform(post("/hotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelSaveRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Hotel saved successfully"));
    }

    @Test
    public void testGetAllHotels() throws Exception {
        when(hotelService.getAllHotels(Collections.emptyMap())).thenReturn(Collections.singletonList(hotelResponse));

        mockMvc.perform(get("/hotel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Grand Plaza"));
    }

    @Test
    public void testGetHotelById() throws Exception {
        Long hotelId = 1L;
        when(hotelService.getHotelById(hotelId)).thenReturn(hotelResponse);

        mockMvc.perform(get("/hotel/" + hotelId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Grand Plaza"));
    }
}
