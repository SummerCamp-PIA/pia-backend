package com.pia.reservation.model;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotel_id;

    private String name;

    private String hotelType; // single
    private String accomudationType; // single
    private Integer accomudatipnTypePrice;

    private String amentities;

    private Integer avgScore = 0;

    private Integer star;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "hotel" , cascade = CascadeType.PERSIST)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel")
    private List<Review> reviews;

    @OneToMany(mappedBy = "hotel")
    private List<File> images;

    private String email;
    private String phoneNo;



}
