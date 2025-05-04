package com.taskmanager.app.controller;

import com.taskmanager.app.dto.JwtAuthenticationResponse;
import com.taskmanager.app.dto.SignInRequest;
import com.taskmanager.app.dto.SignUpRequest;
import com.taskmanager.app.model.User;
import com.taskmanager.app.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
        var signupResponse = authenticationService.signup(signUpRequest);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }
}
