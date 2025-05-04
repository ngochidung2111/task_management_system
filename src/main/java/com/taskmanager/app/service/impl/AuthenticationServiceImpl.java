package com.taskmanager.app.service.impl;

import com.taskmanager.app.dto.JwtAuthenticationResponse;
import com.taskmanager.app.dto.SignInRequest;
import com.taskmanager.app.dto.SignUpRequest;
import com.taskmanager.app.model.Role;
import com.taskmanager.app.model.User;
import com.taskmanager.app.repository.UserRepository;
import com.taskmanager.app.service.AuthenticationService;
import com.taskmanager.app.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public User signup(SignUpRequest signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        String hashed = passwordEncoder.encode(signUpRequest.getPassword());
        user.setPassword(hashed);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }
    public JwtAuthenticationResponse signin(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword()));

        var user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }

}
