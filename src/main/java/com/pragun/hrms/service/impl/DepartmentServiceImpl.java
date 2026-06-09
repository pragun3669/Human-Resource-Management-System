package com.pragun.hrms.service.impl;

import com.pragun.hrms.dto.request.CreateDepartmentRequest;
import com.pragun.hrms.dto.request.UpdateDepartmentRequest;
import com.pragun.hrms.dto.response.DepartmentResponse;
import com.pragun.hrms.entity.Department;
import com.pragun.hrms.exception.DuplicateResourceException;
import com.pragun.hrms.exception.ResourceNotFoundException;
import com.pragun.hrms.repository.DepartmentRepository;
import com.pragun.hrms.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl
        implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponse createDepartment(
            CreateDepartmentRequest request) {

        if (departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "Department already exists");
        }

        Department department = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();

        Department saved =
                departmentRepository.save(department);

        return mapToResponse(saved);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: " + id));

        return mapToResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DepartmentResponse updateDepartment(
            Long id,
            UpdateDepartmentRequest request) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: " + id));

        department.setName(request.getName());
        department.setDescription(request.getDescription());

        Department updated =
                departmentRepository.save(department);

        return mapToResponse(updated);
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: " + id));

        departmentRepository.delete(department);
    }

    private DepartmentResponse mapToResponse(
            Department department) {

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .createdAt(department.getCreatedAt())
                .build();
    }
}