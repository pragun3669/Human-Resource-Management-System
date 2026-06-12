package com.pragun.hrms.service.impl;

import com.pragun.hrms.dto.request.CreateEmployeeRequest;
import com.pragun.hrms.dto.request.UpdateEmployeeRequest;
import com.pragun.hrms.dto.request.UpdateProfileRequest;
import com.pragun.hrms.dto.response.EmployeeResponse;
import com.pragun.hrms.entity.Department;
import com.pragun.hrms.entity.Employee;
import com.pragun.hrms.entity.Role;
import com.pragun.hrms.exception.DuplicateResourceException;
import com.pragun.hrms.exception.ResourceNotFoundException;
import com.pragun.hrms.repository.DepartmentRepository;
import com.pragun.hrms.repository.EmployeeRepository;
import com.pragun.hrms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeResponse createEmployee(
            CreateEmployeeRequest request) {

        if (employeeRepository.existsByEmail(
                request.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists");
        }

        if (request.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException(
                    "Admin creation is not allowed");
        }

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found"));

        Employee manager = null;

        if (request.getManagerId() != null) {

            manager = employeeRepository
                    .findById(request.getManagerId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Manager not found"));
        }

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .role(request.getRole())
                .salary(request.getSalary())
                .phone(request.getPhone())
                .address(request.getAddress())
                .department(department)
                .manager(manager)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Employee saved =
                employeeRepository.save(employee);

        return mapToResponse(saved);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found"));

        return mapToResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EmployeeResponse updateEmployee(
            Long id,
            UpdateEmployeeRequest request) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found"));

        if (request.getDepartmentId() != null) {

            Department department =
                    departmentRepository.findById(
                                    request.getDepartmentId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Department not found"));

            employee.setDepartment(department);
        }

        if (request.getManagerId() != null) {

            Employee manager =
                    employeeRepository.findById(
                                    request.getManagerId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Manager not found"));

            employee.setManager(manager);
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setRole(request.getRole());
        employee.setSalary(request.getSalary());
        employee.setPhone(request.getPhone());
        employee.setAddress(request.getAddress());

        if (request.getIsActive() != null) {
            employee.setIsActive(request.getIsActive());
        }

        employee.setUpdatedAt(LocalDateTime.now());

        Employee updated =
                employeeRepository.save(employee);

        return mapToResponse(updated);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found"));

        employeeRepository.delete(employee);
    }

    private EmployeeResponse mapToResponse(
            Employee employee) {

        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .role(employee.getRole())
                .salary(employee.getSalary())
                .phone(employee.getPhone())
                .address(employee.getAddress())
                .departmentName(
                        employee.getDepartment() != null
                                ? employee.getDepartment().getName()
                                : null
                )
                .managerName(
                        employee.getManager() != null
                                ? employee.getManager().getFirstName()
                                : null
                )
                .isActive(employee.getIsActive())
                .build();
    }
    private Employee getAuthenticatedEmployee() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return employeeRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Authenticated employee not found"
                        ));
    }
    @Override
    public EmployeeResponse getCurrentEmployee() {

        Employee employee =
                getAuthenticatedEmployee();

        return mapToResponse(employee);
    }
    @Override
    public EmployeeResponse updateMyProfile(
            UpdateProfileRequest request) {

        Employee employee =
                getAuthenticatedEmployee();

        employee.setPhone(request.getPhone());
        employee.setAddress(request.getAddress());

        employee.setUpdatedAt(LocalDateTime.now());

        Employee updated =
                employeeRepository.save(employee);

        return mapToResponse(updated);
    }
}