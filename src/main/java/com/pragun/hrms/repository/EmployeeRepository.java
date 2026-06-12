package com.pragun.hrms.repository;

import com.pragun.hrms.entity.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long>,
        JpaSpecificationExecutor<Employee> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findByIsActiveTrue();

    Page<Employee> findByIsActiveTrue(
            Pageable pageable
    );
    long countByIsActiveTrue();

    long countByDepartmentId(Long departmentId);

    boolean existsByEmail(String email);
}