package com.taskmanager.app.service;

import com.taskmanager.app.dto.JwtAuthenticationResponse;
import com.taskmanager.app.dto.SignInRequest;
import com.taskmanager.app.dto.SignUpRequest;
import com.taskmanager.app.model.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);
}
