package com.pia.reservation.controller;


import com.pia.reservation.dto.response.FileResponse;
import com.pia.reservation.model.File;
import com.pia.reservation.util.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;


    /*@PostMapping("")
    public ResponseEntity saveFiles(
            @RequestParam("files") MultipartFile [] multipartFiles, @RequestParam("hotel_id") Long hotelId){
        try {
            fileService.saveFiles(multipartFiles, hotelId);
            return ResponseEntity.ok("success");
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }*/

    @GetMapping("/{hotel_id}")
    public ResponseEntity<List<FileResponse>> getFiles(@PathVariable Long hotel_id) throws IOException {
       return ResponseEntity.ok(fileService.getFiles(hotel_id));
    }



}
