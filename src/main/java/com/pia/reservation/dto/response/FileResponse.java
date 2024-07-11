package com.pia.reservation.dto.response;


import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponse {

    String fileName;
    byte[] file;
}
