package com.pragun.hrms.controller;

import com.pragun.hrms.dto.response.ApiResponse;
import com.pragun.hrms.dto.response.AttendanceResponse;
import com.pragun.hrms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/clock-in")
    public ResponseEntity<ApiResponse<AttendanceResponse>>
    clockIn() {

        AttendanceResponse response =
                attendanceService.clockIn();

        return ResponseEntity.ok(
                ApiResponse.<AttendanceResponse>builder()
                        .success(true)
                        .message("Clock in successful")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/clock-out")
    public ResponseEntity<ApiResponse<AttendanceResponse>>
    clockOut() {

        AttendanceResponse response =
                attendanceService.clockOut();

        return ResponseEntity.ok(
                ApiResponse.<AttendanceResponse>builder()
                        .success(true)
                        .message("Clock out successful")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>>
    getMyAttendance() {

        return ResponseEntity.ok(
                ApiResponse
                        .<List<AttendanceResponse>>builder()
                        .success(true)
                        .message(
                                "Attendance fetched successfully")
                        .data(
                                attendanceService
                                        .getMyAttendance())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}