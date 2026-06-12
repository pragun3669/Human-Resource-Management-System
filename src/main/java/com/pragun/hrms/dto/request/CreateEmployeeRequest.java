package com.pragun.hrms.dto.request;

import com.pragun.hrms.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateEmployeeRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotNull
    private Role role;

    @NotNull
    private BigDecimal salary;

    private String phone;

    private String address;

    private Long departmentId;

    private Long managerId;
}