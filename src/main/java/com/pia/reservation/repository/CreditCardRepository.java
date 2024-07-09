package com.pia.reservation.repository;

import com.pia.reservation.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {

    Optional<CreditCard> findByCardNo(String cardNo);
}
