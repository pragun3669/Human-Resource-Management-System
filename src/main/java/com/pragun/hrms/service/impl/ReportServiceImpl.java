package com.pragun.hrms.service.impl;

import com.pragun.hrms.dto.response.DashboardResponse;
import com.pragun.hrms.entity.LeaveStatus;
import com.pragun.hrms.repository.DepartmentRepository;
import com.pragun.hrms.repository.EmployeeRepository;
import com.pragun.hrms.repository.LeaveRepository;
import com.pragun.hrms.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl
        implements ReportService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LeaveRepository leaveRepository;

    @Override
    public DashboardResponse getDashboard() {

        return DashboardResponse.builder()
                .totalEmployees(
                        employeeRepository.count())
                .activeEmployees(
                        employeeRepository
                                .countByIsActiveTrue())
                .totalDepartments(
                        departmentRepository.count())
                .pendingLeaves(
                        leaveRepository.countByStatus(
                                LeaveStatus.PENDING))
                .build();
    }
}