package com.pragun.hrms.config;

import com.pragun.hrms.entity.Employee;
import com.pragun.hrms.entity.Role;
import com.pragun.hrms.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (employeeRepository.existsByEmail(
                "admin@hrms.com")) {
            return;
        }

        Employee admin = Employee.builder()
                .firstName("System")
                .lastName("Administrator")
                .email("admin@hrms.com")
                .password(
                        passwordEncoder.encode("Admin@123")
                )
                .role(Role.ADMIN)
                .salary(BigDecimal.ZERO)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        employeeRepository.save(admin);

        System.out.println(
                "Default admin created successfully");
    }
}