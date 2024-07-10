package com.pia.reservation.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;

import java.lang.reflect.Field;
import java.util.List;

public class PredicateLogger {

    public static void logPredicates(List<Predicate> predicates) {
        for (Predicate predicate : predicates) {
            System.out.println("Predicate: " + predicate);
            for (Field field : predicate.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(predicate);
                    System.out.println(field.getName() + " = " + value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("-------------------------------");
        }
    }

    public static void logPredicate(Predicate predicate) {
        System.out.println("Predicate: " + predicate);
        for (Field field : predicate.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(predicate);
                System.out.println(field.getName() + " = " + value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println("-------------------------------");
    }
}