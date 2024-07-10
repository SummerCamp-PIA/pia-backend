package com.pia.reservation.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guest_id;

    private String name;
    private String surname;
    private Date birthDate;
    private String identityNo;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;



}
