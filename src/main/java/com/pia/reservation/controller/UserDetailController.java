package com.pia.reservation.controller;

import com.pia.reservation.model.UserDetail;
import com.pia.reservation.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-details")
public class UserDetailController {
    @Autowired
    private UserDetailService userDetailService;

    @GetMapping("/{id}")
    public UserDetail getUserDetailById(@PathVariable Long id) {
        return userDetailService.getUserDetailById(id);
    }

    @PostMapping
    public UserDetail createUserDetail(@RequestBody UserDetail userDetail) {
        return userDetailService.saveUserDetail(userDetail);
    }

    @DeleteMapping("/{id}")
    public void deleteUserDetail(@PathVariable Long id) {
        userDetailService.deleteUserDetail(id);
    }
}