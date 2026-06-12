package com.pragun.hrms.dto.response;

import com.pragun.hrms.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EmployeeResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private BigDecimal salary;

    private String phone;

    private String address;

    private String departmentName;

    private String managerName;

    private Boolean isActive;
}