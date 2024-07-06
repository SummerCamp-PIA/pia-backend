package com.pia.reservation.repository;

import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository  extends JpaRepository<Location,Long> {
}
