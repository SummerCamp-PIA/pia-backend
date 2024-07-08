package com.pia.reservation.util;

import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.Location;
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
                            predicates.add(criteriaBuilder.like(root.get("amenities"), "%" + amenity.trim() + "%"));
                        }
                        break;
                    case "hotelType": predicates.add(criteriaBuilder.equal(root.get("hotelType"), value));
                    break;

                }
            }
            if(params.containsKey("sortBy")){
                String sortBy = params.get("sortBy");
                if("avg_score_asc".equalsIgnoreCase(sortBy)){
                    query.orderBy(criteriaBuilder.asc(root.get("avg_score_asc")));
                }
                else  if ("avg_score_desc".equalsIgnoreCase(sortBy)){}
                query.orderBy(criteriaBuilder.desc(root.get("avg_score_asc")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
