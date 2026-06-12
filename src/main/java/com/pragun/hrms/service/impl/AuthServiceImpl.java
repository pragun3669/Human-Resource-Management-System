package com.pragun.hrms.service.impl;

import com.pragun.hrms.dto.request.LoginRequest;
import com.pragun.hrms.dto.response.LoginResponse;
import com.pragun.hrms.entity.Employee;
import com.pragun.hrms.repository.EmployeeRepository;
import com.pragun.hrms.security.JwtService;
import com.pragun.hrms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {

        Employee employee = employeeRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "Invalid email or password"
                        ));

        if (!passwordEncoder.matches(
                request.getPassword(),
                employee.getPassword())) {

            throw new BadCredentialsException(
                    "Invalid email or password"
            );
        }

        String token =
                jwtService.generateToken(employee.getEmail());

        return LoginResponse.builder()
                .token(token)
                .employeeId(employee.getId())
                .email(employee.getEmail())
                .role(employee.getRole())
                .build();
    }
}