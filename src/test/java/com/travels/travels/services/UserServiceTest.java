package com.travels.travels.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.travels.travels.controllers.AuthResponse;
import com.travels.travels.controllers.LoginRequest;
import com.travels.travels.models.User;
import com.travels.travels.repositories.UserRepository;
import com.travels.travels.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddUserSuccess() {

        User user = new User(302, "Paloma Urban Fashion", "ypuntoenboca@gmail.com", "juanlascaras6", new ArrayList<>());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.addUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getEmail(), createdUser.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    public void testLoginUserSuccess() {

        User user = new User();
        user.setName("Paloma Urban Fashion");
        user.setEmail("ypuntoenboca@gmail.com");
        user.setPassword(passwordEncoder.encode("juanlascaras6"));

        when(userRepository.findByEmail("ypuntoenboca@gmail.com")).thenReturn(Optional.of(user));

        String token = "sampleToken";
        when(jwtUtil.generateToken(user.getEmail())).thenReturn(token);

        LoginRequest loginRequest = new LoginRequest("ypuntoenboca@gmail.com", "juanlascaras6");

        AuthResponse authResponse = userService.login(loginRequest);

        assertNotNull(authResponse);
        assertEquals(token, authResponse.getToken());
    }
}