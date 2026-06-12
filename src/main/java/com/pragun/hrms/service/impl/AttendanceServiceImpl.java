package com.pragun.hrms.service.impl;

import com.pragun.hrms.dto.response.AttendanceResponse;
import com.pragun.hrms.entity.Attendance;
import com.pragun.hrms.entity.Employee;
import com.pragun.hrms.exception.ResourceNotFoundException;
import com.pragun.hrms.repository.AttendanceRepository;
import com.pragun.hrms.repository.EmployeeRepository;
import com.pragun.hrms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl
        implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

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
                                "Employee not found"));
    }

    @Override
    public AttendanceResponse clockIn() {

        Employee employee =
                getAuthenticatedEmployee();

        LocalDate today =
                LocalDate.now();

        attendanceRepository
                .findByEmployeeAndDate(
                        employee,
                        today
                )
                .ifPresent(attendance -> {
                    throw new IllegalArgumentException(
                            "Already clocked in today"
                    );
                });

        Attendance attendance =
                Attendance.builder()
                        .employee(employee)
                        .date(today)
                        .clockInTime(
                                LocalDateTime.now())
                        .createdAt(
                                LocalDateTime.now())
                        .updatedAt(
                                LocalDateTime.now())
                        .build();

        Attendance saved =
                attendanceRepository.save(attendance);

        return mapToResponse(saved);
    }

    @Override
    public AttendanceResponse clockOut() {

        Employee employee =
                getAuthenticatedEmployee();

        Attendance attendance =
                attendanceRepository
                        .findByEmployeeAndDate(
                                employee,
                                LocalDate.now())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Clock in first"));

        if (attendance.getClockOutTime()
                != null) {

            throw new IllegalArgumentException(
                    "Already clocked out"
            );
        }

        attendance.setClockOutTime(
                LocalDateTime.now());

        attendance.setUpdatedAt(
                LocalDateTime.now());

        Attendance updated =
                attendanceRepository
                        .save(attendance);

        return mapToResponse(updated);
    }

    @Override
    public List<AttendanceResponse>
    getMyAttendance() {

        Employee employee =
                getAuthenticatedEmployee();

        return attendanceRepository
                .findByEmployee(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AttendanceResponse mapToResponse(
            Attendance attendance) {

        return AttendanceResponse.builder()
                .id(attendance.getId())
                .employeeName(
                        attendance.getEmployee()
                                .getFirstName()
                                + " "
                                + attendance.getEmployee()
                                .getLastName())
                .date(attendance.getDate())
                .clockInTime(
                        attendance.getClockInTime())
                .clockOutTime(
                        attendance.getClockOutTime())
                .build();
    }
}