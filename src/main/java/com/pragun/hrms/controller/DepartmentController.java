package com.pragun.hrms.controller;

import com.pragun.hrms.dto.request.CreateDepartmentRequest;
import com.pragun.hrms.dto.request.UpdateDepartmentRequest;
import com.pragun.hrms.dto.response.ApiResponse;
import com.pragun.hrms.dto.response.DepartmentResponse;
import com.pragun.hrms.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>>
    createDepartment(
            @Valid @RequestBody CreateDepartmentRequest request) {

        DepartmentResponse response =
                departmentService.createDepartment(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<DepartmentResponse>builder()
                                .success(true)
                                .message("Department created successfully")
                                .data(response)
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>>
    getDepartmentById(@PathVariable Long id) {

        DepartmentResponse response =
                departmentService.getDepartmentById(id);

        return ResponseEntity.ok(
                ApiResponse.<DepartmentResponse>builder()
                        .success(true)
                        .message("Department fetched successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>>
    getAllDepartments() {

        List<DepartmentResponse> response =
                departmentService.getAllDepartments();

        return ResponseEntity.ok(
                ApiResponse.<List<DepartmentResponse>>builder()
                        .success(true)
                        .message("Departments fetched successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>>
    updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDepartmentRequest request) {

        DepartmentResponse response =
                departmentService.updateDepartment(id, request);

        return ResponseEntity.ok(
                ApiResponse.<DepartmentResponse>builder()
                        .success(true)
                        .message("Department updated successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>>
    deleteDepartment(@PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Department deleted successfully")
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}