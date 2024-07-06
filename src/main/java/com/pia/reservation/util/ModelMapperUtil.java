package com.pia.reservation.util;


import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperUtil {

    public static ModelMapper modelMapper;

    @PostConstruct
    public void init(){
        modelMapper = new ModelMapper();
    }



}
