package com.pragun.hrms.controller;

import com.pragun.hrms.dto.response.ApiResponse;
import com.pragun.hrms.dto.response.DashboardResponse;
import com.pragun.hrms.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<
            ApiResponse<DashboardResponse>>
    getDashboard() {

        return ResponseEntity.ok(
                ApiResponse
                        .<DashboardResponse>builder()
                        .success(true)
                        .message("Dashboard fetched")
                        .data(
                                reportService.getDashboard())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}