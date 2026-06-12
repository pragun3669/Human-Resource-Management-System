package com.pragun.hrms.controller;

import com.pragun.hrms.dto.request.LoginRequest;
import com.pragun.hrms.dto.response.ApiResponse;
import com.pragun.hrms.dto.response.LoginResponse;
import com.pragun.hrms.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Login successful")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}