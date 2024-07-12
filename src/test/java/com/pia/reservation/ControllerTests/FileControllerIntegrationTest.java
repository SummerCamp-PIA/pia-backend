package com.pia.reservation.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pia.reservation.dto.response.FileResponse;
import com.pia.reservation.model.Hotel;
import com.pia.reservation.repository.FileRepository;
import com.pia.reservation.service.HotelService;
import com.pia.reservation.util.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private FileRepository fileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<MultipartFile[]> multipartFilesCaptor;

    private Hotel hotel;
    private MockMultipartFile[] mockFiles;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        hotel = new Hotel();
        hotel.setHotel_id(1L);
        hotel.setName("Test Hotel");

        mockFiles = new MockMultipartFile[]{
                new MockMultipartFile("files", "file1.jpg", MediaType.IMAGE_JPEG_VALUE, "File 1 Content".getBytes()),
                new MockMultipartFile("files", "file2.jpg", MediaType.IMAGE_JPEG_VALUE, "File 2 Content".getBytes())
        };

        when(hotelService.getHotel(1L)).thenReturn(hotel);
    }

    @Test
    public void testSaveFiles() throws Exception {
        mockMvc.perform(multipart("/file")
                        .file(mockFiles[0])
                        .file(mockFiles[1])
                        .param("hotel_id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        verify(fileService, times(1)).saveFiles(multipartFilesCaptor.capture(), any(Long.class));
    }

    @Test
    public void testGetFiles() throws Exception {
        FileResponse fileResponse = FileResponse.builder()
                .fileName("file1.jpg")
                .file("File 1 Content".getBytes())
                .build();

        when(fileService.getFiles(1L)).thenReturn(Collections.singletonList(fileResponse));

        mockMvc.perform(get("/file/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fileName").value("file1.jpg"));
    }
}
