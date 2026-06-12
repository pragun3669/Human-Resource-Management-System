package com.pragun.hrms.dto.request;

import com.pragun.hrms.entity.Role;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateEmployeeRequest {

    private String firstName;

    private String lastName;

    private Role role;

    private BigDecimal salary;

    private String phone;

    private String address;

    private Long departmentId;

    private Long managerId;

    private Boolean isActive;
}