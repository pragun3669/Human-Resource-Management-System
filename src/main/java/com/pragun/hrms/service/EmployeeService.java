package com.pragun.hrms.service;

import com.pragun.hrms.dto.request.CreateEmployeeRequest;
import com.pragun.hrms.dto.request.UpdateEmployeeRequest;
import com.pragun.hrms.dto.response.EmployeeResponse;
import com.pragun.hrms.dto.request.UpdateProfileRequest;
import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(
            CreateEmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse updateEmployee(
            Long id,
            UpdateEmployeeRequest request);
    EmployeeResponse getCurrentEmployee();

    EmployeeResponse updateMyProfile(
            UpdateProfileRequest request);

    void deleteEmployee(Long id);
}