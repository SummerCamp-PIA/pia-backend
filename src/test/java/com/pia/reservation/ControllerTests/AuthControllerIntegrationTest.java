package com.pia.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pia.reservation.dto.request.AuthenticationRequest;
import com.pia.reservation.dto.response.AuthenticationResponse;
import com.pia.reservation.model.auth.Role;
import com.pia.reservation.model.auth.User;
import com.pia.reservation.repository.UserRepository;
import com.pia.reservation.security.JwtUtil;
import com.pia.reservation.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(Role.USER);

        authenticationRequest = AuthenticationRequest.builder()
                .username("testuser")
                .password("password")
                .build();
    }

    @Test
    public void testRegisterUser() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    public void testCreateAuthenticationToken() throws Exception {
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userService.findByUsername("testuser")).thenReturn(
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                )
        );
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new AuthenticationResponse("fake-jwt-token"))));
    }
}
