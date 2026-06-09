package com.pragun.hrms.service;

import com.pragun.hrms.dto.request.CreateDepartmentRequest;
import com.pragun.hrms.dto.request.UpdateDepartmentRequest;
import com.pragun.hrms.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(
            CreateDepartmentRequest request);

    DepartmentResponse getDepartmentById(Long id);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse updateDepartment(
            Long id,
            UpdateDepartmentRequest request);

    void deleteDepartment(Long id);
}