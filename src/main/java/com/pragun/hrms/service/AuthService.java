package com.pragun.hrms.service;

import com.pragun.hrms.dto.request.LoginRequest;
import com.pragun.hrms.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}