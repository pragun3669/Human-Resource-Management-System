package com.pragun.hrms.controller;

import com.pragun.hrms.dto.request.LeaveDecisionRequest;
import com.pragun.hrms.dto.request.LeaveRequest;
import com.pragun.hrms.dto.response.ApiResponse;
import com.pragun.hrms.dto.response.LeaveResponse;
import com.pragun.hrms.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResponse<LeaveResponse>>
    applyLeave(
            @RequestBody LeaveRequest request) {

        LeaveResponse response =
                leaveService.applyLeave(request);

        return ResponseEntity.ok(
                ApiResponse.<LeaveResponse>builder()
                        .success(true)
                        .message("Leave applied successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>>
    getMyLeaves() {

        return ResponseEntity.ok(
                ApiResponse.<List<LeaveResponse>>builder()
                        .success(true)
                        .message("Leaves fetched successfully")
                        .data(
                                leaveService.getMyLeaves())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>>
    getPendingLeaves() {

        return ResponseEntity.ok(
                ApiResponse.<List<LeaveResponse>>builder()
                        .success(true)
                        .message("Pending leaves fetched")
                        .data(
                                leaveService.getPendingLeaves())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<LeaveResponse>>
    approveLeave(
            @PathVariable Long id,
            @RequestBody LeaveDecisionRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<LeaveResponse>builder()
                        .success(true)
                        .message("Leave approved")
                        .data(
                                leaveService.approveLeave(
                                        id,
                                        request))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<LeaveResponse>>
    rejectLeave(
            @PathVariable Long id,
            @RequestBody LeaveDecisionRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<LeaveResponse>builder()
                        .success(true)
                        .message("Leave rejected")
                        .data(
                                leaveService.rejectLeave(
                                        id,
                                        request))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}