package com.pia.reservation.util;

import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.Location;
import com.pia.reservation.model.Reservation;
import com.pia.reservation.model.Room;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SpecificationBuilder<T> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private String checkInDateString;
    private String checkOutDateString;
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
                        Join<Hotel, Room> roomTypeJoin = root.join("rooms");
                        predicates.add(criteriaBuilder.equal(roomTypeJoin.get(key), value));
                    break;

                    case "maxOccupancy":
                        Join<Hotel, Room> maxOccupancyJoin = root.join("rooms");
                        predicates.add(criteriaBuilder.equal(maxOccupancyJoin.get(key), value));
                        break;
                    case "bedNo":
                        Join<Hotel, Room> bedNoJoin = root.join("rooms");
                        predicates.add(criteriaBuilder.equal(bedNoJoin.get(key), value));
                        break;

                    case "price":
                        Join<Hotel, Room> minPriceJoin = root.join("rooms");
                        String prices[] = value.split("-");

                        int min = Integer.valueOf(prices[0]);
                        int max = Integer.valueOf(prices[1]);
                        System.out.println(min + "min - max " + max);
                        predicates.add(criteriaBuilder.greaterThan(minPriceJoin.get(key), min));
                        predicates.add(criteriaBuilder.lessThan(minPriceJoin.get(key), max));

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
            if(params.containsKey("checkInDate")&&params.containsKey("checkOutDate")){
                checkInDateString = params.get("checkInDate");
                checkOutDateString = params.get("checkOutDate");
            }
            if (checkInDateString != null && checkOutDateString != null) {
                Date checkInDate;
                Date checkOutDate;
                try {
                    checkInDate = DATE_FORMAT.parse(checkInDateString);
                    checkOutDate = DATE_FORMAT.parse(checkOutDateString);
                } catch (ParseException e) {
                    throw new RuntimeException("Invalid date format, expected yyyy-MM-dd", e);
                }

                Join<Hotel, Room> roomJoin = root.join("rooms");
                Subquery<Reservation> subquery = query.subquery(Reservation.class);
                Root<Reservation> subRoot = subquery.from(Reservation.class);
                subquery.select(subRoot);
                subquery.where(
                        criteriaBuilder.equal(subRoot.get("room"), roomJoin),
                        criteriaBuilder.or(
                                criteriaBuilder.between(subRoot.get("checkInDate"), checkInDate, checkOutDate),
                                criteriaBuilder.between(subRoot.get("checkOutDate"), checkInDate, checkOutDate),
                                criteriaBuilder.and(
                                        criteriaBuilder.lessThanOrEqualTo(subRoot.get("checkInDate"), checkInDate),
                                        criteriaBuilder.greaterThanOrEqualTo(subRoot.get("checkOutDate"), checkOutDate)
                                )
                        )
                );
                predicates.add(criteriaBuilder.not(criteriaBuilder.exists(subquery)));
            }
            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            PredicateLogger.logPredicate(finalPredicate);




            return finalPredicate;
        };
    }
}
