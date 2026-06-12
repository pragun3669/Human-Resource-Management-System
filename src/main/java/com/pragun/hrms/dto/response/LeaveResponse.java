package com.pragun.hrms.dto.response;

import com.pragun.hrms.entity.LeaveStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LeaveResponse {

    private Long id;

    private String employeeName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    private LeaveStatus status;

    private String managerRemarks;
}