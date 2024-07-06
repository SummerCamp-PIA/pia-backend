package com.pia.reservation.repository;

import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<Review,Long> {
}
