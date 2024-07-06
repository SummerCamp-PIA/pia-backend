package com.pia.reservation.repository;

import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
}
