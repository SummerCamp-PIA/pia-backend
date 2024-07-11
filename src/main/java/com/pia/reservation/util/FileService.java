package com.pia.reservation.util;


import com.pia.reservation.dto.response.FileResponse;
import com.pia.reservation.dto.response.HotelResponse;
import com.pia.reservation.model.File;
import com.pia.reservation.model.Hotel;
import com.pia.reservation.repository.FileRepository;
import com.pia.reservation.repository.HotelRepository;
import com.pia.reservation.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadDir = "uploads/";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private HotelRepository hotelRepository;



    public void saveFiles(MultipartFile [] multipartFiles, Hotel hotel) throws IOException {



        int hotelImageCount = hotel.getImages().size();
        if(hotel != null){



            Path hotelDir = Paths.get(uploadDir + hotel.getName().replace(" ", "_"));
            Files.createDirectories(hotelDir); // Bu dizin yoksa, gerekli tüm ara dizinlerle birlikte oluşturur

            for(MultipartFile file : multipartFiles){
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                // Otelin sahip olduğu resim sayısının + 1
                String newFilename = hotel.getName() + "_" + (hotelImageCount + 1) + fileExtension;
                hotelImageCount++;

                // Dosya yolunu oluştur ve dosyayı kaydet
                Path filePath = hotelDir.resolve(newFilename);
                Files.write(filePath, file.getBytes());

                File fileEntity = File.builder()
                        .fileName(newFilename)
                        .fileType(fileExtension)
                        .path(filePath.toString())
                        .hotel(hotel)
                        .build();

                fileRepository.save(fileEntity);
            }
        }
    }


    public List<FileResponse> getFiles(Long hotelId) throws IOException {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        List<File> files = fileRepository.findAllByHotel(hotel);


        ArrayList<FileResponse> fileResponses = new ArrayList<>();

        for(File file : files){
            String filePath = file.getPath();

            Path path = Paths.get(filePath);
            byte[] data = Files.readAllBytes(path);

            FileResponse fileResponse = FileResponse.builder()
                    .file(data)
                    .fileName(file.getFileName())
                    .build();

            fileResponses.add(fileResponse);

        }

        return fileResponses;


    }



}
