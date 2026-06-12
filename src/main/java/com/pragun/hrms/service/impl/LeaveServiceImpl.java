package com.pragun.hrms.service.impl;

import com.pragun.hrms.dto.request.LeaveDecisionRequest;
import com.pragun.hrms.dto.request.LeaveRequest;
import com.pragun.hrms.dto.response.LeaveResponse;
import com.pragun.hrms.entity.Employee;
import com.pragun.hrms.entity.Leave;
import com.pragun.hrms.entity.LeaveStatus;
import com.pragun.hrms.exception.ResourceNotFoundException;
import com.pragun.hrms.repository.EmployeeRepository;
import com.pragun.hrms.repository.LeaveRepository;
import com.pragun.hrms.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    private Employee getAuthenticatedEmployee() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null) {
            throw new ResourceNotFoundException(
                    "No authenticated user found"
            );
        }

        String email = authentication.getName();

        return employeeRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found"
                        ));
    }

    @Override
    public LeaveResponse applyLeave(
            LeaveRequest request) {

        Employee employee =
                getAuthenticatedEmployee();

        if (request.getEndDate()
                .isBefore(request.getStartDate())) {

            throw new IllegalArgumentException(
                    "End date cannot be before start date"
            );
        }

        Leave leave = Leave.builder()
                .employee(employee)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .reason(request.getReason())
                .status(LeaveStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Leave saved =
                leaveRepository.save(leave);

        return mapToResponse(saved);
    }

    @Override
    public List<LeaveResponse> getMyLeaves() {

        Employee employee =
                getAuthenticatedEmployee();

        return leaveRepository
                .findByEmployee(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<LeaveResponse> getPendingLeaves() {

        return leaveRepository
                .findByStatus(LeaveStatus.PENDING)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public LeaveResponse approveLeave(
            Long leaveId,
            LeaveDecisionRequest request) {

        Leave leave =
                leaveRepository.findById(leaveId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Leave not found"
                                ));

        if (leave.getStatus()
                != LeaveStatus.PENDING) {

            throw new IllegalArgumentException(
                    "Leave already processed"
            );
        }

        leave.setStatus(
                LeaveStatus.APPROVED);

        leave.setManagerRemarks(
                request.getManagerRemarks());

        leave.setUpdatedAt(
                LocalDateTime.now());

        Leave updated =
                leaveRepository.save(leave);

        return mapToResponse(updated);
    }

    @Override
    public LeaveResponse rejectLeave(
            Long leaveId,
            LeaveDecisionRequest request) {

        Leave leave =
                leaveRepository.findById(leaveId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Leave not found"
                                ));

        if (leave.getStatus()
                != LeaveStatus.PENDING) {

            throw new IllegalArgumentException(
                    "Leave already processed"
            );
        }

        leave.setStatus(
                LeaveStatus.REJECTED);

        leave.setManagerRemarks(
                request.getManagerRemarks());

        leave.setUpdatedAt(
                LocalDateTime.now());

        Leave updated =
                leaveRepository.save(leave);

        return mapToResponse(updated);
    }

    private LeaveResponse mapToResponse(
            Leave leave) {

        return LeaveResponse.builder()
                .id(leave.getId())
                .employeeName(
                        leave.getEmployee().getFirstName()
                                + " "
                                + leave.getEmployee().getLastName()
                )
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .reason(leave.getReason())
                .status(leave.getStatus())
                .managerRemarks(
                        leave.getManagerRemarks()
                )
                .build();
    }
}