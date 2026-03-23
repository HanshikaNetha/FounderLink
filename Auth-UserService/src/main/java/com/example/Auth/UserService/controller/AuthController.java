package com.example.Auth.UserService.controller;

import com.example.Auth.UserService.dto.LoginRequest;
import com.example.Auth.UserService.dto.LoginResponse;
import com.example.Auth.UserService.dto.RegisterRequest;
import com.example.Auth.UserService.dto.RegisterResponse;
import com.example.Auth.UserService.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse=authService.login(request);
        return ResponseEntity.ok(loginResponse);
    }
}
