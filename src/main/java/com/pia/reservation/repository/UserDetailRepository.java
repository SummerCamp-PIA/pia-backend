package com.pia.reservation.repository;

import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail,Long> {
}
