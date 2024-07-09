package com.pia.reservation.util;

import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.Location;
import com.pia.reservation.model.Reservation;
import com.pia.reservation.model.Room;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecificationBuilder<T> {
    public Specification<T> build(Map<String,String> params) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if(params == null || params.isEmpty()) {

                return criteriaBuilder.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();

            for(Map.Entry<String, String> entry : params.entrySet()) {

                String key = entry.getKey();
                String value = entry.getValue();
                switch (key){
                    case "city":
                    case "state":
                    case "country":
                        Join<Hotel, Location> locationJoin = root.join("location");
                        predicates.add(criteriaBuilder.equal(locationJoin.get(key), value));


                        break;
                    case "amentities":
                        String[] amenitiesArray = value.split(",");
                        for (String amenity : amenitiesArray) {
                            predicates.add(criteriaBuilder.like(root.get("amentities"), "%" + amenity.trim() + "%"));
                        }
                        break;
                    case "hotelType": predicates.add(criteriaBuilder.equal(root.get("hotelType"), value));
                    break;
                    case "roomAmentites":
                        Join<Hotel, Room> roomJoin = root.join("rooms");
                        String[] roomAmenitiesArray = value.split(",");
                        for(String roomAmenity : roomAmenitiesArray){
                            System.out.println("Filtering for room amenity: " + roomAmenity);
                            System.out.println(roomAmenity.trim());
                            predicates.add(criteriaBuilder.like(roomJoin.get("roomAmentites"), "%" + roomAmenity.trim() + "%"));
                        }
                        //predicates.add(criteriaBuilder.equal(roomJoin.get(key), value));
                        break;
                    case "roomType":
                        Join<Hotel, Room> roomTypeJoin = root.join("roomType");
                        predicates.add(criteriaBuilder.equal(roomTypeJoin.get(key), value));
                    break;

                    case "maxOccupancy":
                        Join<Hotel, Room> maxOccupancyJoin = root.join("maxOccupancy");
                        predicates.add(criteriaBuilder.equal(maxOccupancyJoin.get(key), value));
                        break;
                    case "bedNo":
                        Join<Hotel, Room> bedNoJoin = root.join("bedNo");
                        predicates.add(criteriaBuilder.equal(bedNoJoin.get(key), value));
                        break;

                }
            }
            if(params.containsKey("sortBy")){
                String sortBy = params.get("sortBy");
                if("avg_score_asc".equalsIgnoreCase(sortBy)){
                    query.orderBy(criteriaBuilder.asc(root.get("avgScore")));
                }
                else  if ("avg_score_desc".equalsIgnoreCase(sortBy)){
                    query.orderBy(criteriaBuilder.desc(root.get("avgScore")));
                }

            }
            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            System.out.println("Final predicate: " + finalPredicate.toString());

            return finalPredicate;
        };
    }
}
