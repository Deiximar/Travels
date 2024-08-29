package com.travels.travels.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.travels.errors.ExistingEmailError;
import com.travels.errors.UnauthorizedException;
import com.travels.errors.UserNotFoundException;
import com.travels.travels.controllers.AuthResponse;
import com.travels.travels.controllers.LoginRequest;
import com.travels.travels.controllers.RegisterRequest;
import com.travels.travels.models.User;
import com.travels.travels.repositories.UserRepository;
import com.travels.travels.security.JwtUtil;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public Optional<User> getUserByID(int id) {
        return userRepository.findById(id);
    }

    public List<User> getUser() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new ExistingEmailError();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No existe un usuario con este correo electrónico"));
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        // Verificar si el email ya está registrado
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ExistingEmailError();
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public int getUserId(String token) {
        try {
            String user = jwtUtil.extractUsername(token);
            return userRepository.findByEmail(user)
                    .orElseThrow(() -> new UnauthorizedException("Usuario no encontrado")).getId();

        } catch (JwtException e) {
            throw new UnauthorizedException("Token inválido o expirado");

        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("Usuario no encontrado para el token proporcionado");
        }

    }
}