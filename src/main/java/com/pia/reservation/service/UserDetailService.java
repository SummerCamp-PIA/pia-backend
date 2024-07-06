package com.pia.reservation.service;

import com.pia.reservation.model.UserDetail;
import com.pia.reservation.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService {
    @Autowired
    private UserDetailRepository userDetailRepository;

    public UserDetail getUserDetailById(Long id) {
        return userDetailRepository.findById(id).orElse(null);
    }

    public UserDetail saveUserDetail(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }

    public void deleteUserDetail(Long id) {
        userDetailRepository.deleteById(id);
    }
}