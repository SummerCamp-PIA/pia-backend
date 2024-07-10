package com.pia.reservation.repository;

import com.pia.reservation.model.File;
import com.pia.reservation.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Long> {

    File findByFileName(String fileName);
    List<File> findAllByHotel(Hotel hotel);
}
