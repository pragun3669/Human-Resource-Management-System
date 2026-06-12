package com.pragun.hrms.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private long totalEmployees;

    private long activeEmployees;

    private long totalDepartments;

    private long pendingLeaves;
}