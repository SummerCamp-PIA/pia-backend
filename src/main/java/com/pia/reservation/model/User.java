package com.pia.reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDetail userDetail;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
