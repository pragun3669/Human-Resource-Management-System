package com.pragun.hrms.repository;

import com.pragun.hrms.entity.Employee;
import com.pragun.hrms.entity.Leave;
import com.pragun.hrms.entity.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository
        extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployee(Employee employee);

    List<Leave> findByStatus(LeaveStatus status);
}