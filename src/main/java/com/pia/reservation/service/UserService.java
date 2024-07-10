package com.pia.reservation.service;

import com.pia.reservation.dto.response.HotelResponse;
import com.pia.reservation.dto.response.RoomDto;
import com.pia.reservation.dto.response.UserDetailResponse;
import com.pia.reservation.model.Hotel;
import com.pia.reservation.model.Room;
import com.pia.reservation.model.auth.User;
import com.pia.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.pia.reservation.util.ModelMapperUtil.modelMapper;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetails findByUsername(String username) throws Exception {
/*
        User user = userRepository.findByUsername(username).orElseThrow();
        return modelMapper.map(user, UserDetailResponse.class);*/
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new Exception("User not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    }
}
