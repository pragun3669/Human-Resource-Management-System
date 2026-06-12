package com.pragun.hrms.dto.response;

import com.pragun.hrms.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;

    private Long employeeId;

    private String email;

    private Role role;
}