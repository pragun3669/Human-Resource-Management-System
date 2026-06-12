package com.pragun.hrms.controller;

import com.pragun.hrms.dto.request.CreateEmployeeRequest;
import com.pragun.hrms.dto.request.UpdateEmployeeRequest;
import com.pragun.hrms.dto.request.UpdateProfileRequest;
import com.pragun.hrms.dto.response.ApiResponse;
import com.pragun.hrms.dto.response.EmployeeResponse;
import com.pragun.hrms.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>>
    createEmployee(
            @Valid @RequestBody CreateEmployeeRequest request) {

        EmployeeResponse response =
                employeeService.createEmployee(request);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Employee created successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>>
    getEmployee(@PathVariable Long id) {

        EmployeeResponse response =
                employeeService.getEmployeeById(id);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Employee fetched successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>>
    getAllEmployees() {

        List<EmployeeResponse> response =
                employeeService.getAllEmployees();

        return ResponseEntity.ok(
                ApiResponse.<List<EmployeeResponse>>builder()
                        .success(true)
                        .message("Employees fetched successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>>
    updateEmployee(
            @PathVariable Long id,
            @RequestBody UpdateEmployeeRequest request) {

        EmployeeResponse response =
                employeeService.updateEmployee(id, request);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Employee updated successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>>
    deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Employee deleted successfully")
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<EmployeeResponse>>
    getCurrentEmployee() {

        EmployeeResponse response =
                employeeService.getCurrentEmployee();

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Profile fetched successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<EmployeeResponse>>
    updateMyProfile(
            @RequestBody UpdateProfileRequest request) {

        EmployeeResponse response =
                employeeService.updateMyProfile(request);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Profile updated successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}