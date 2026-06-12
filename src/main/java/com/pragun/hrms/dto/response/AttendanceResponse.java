package com.pragun.hrms.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class AttendanceResponse {

    private Long id;

    private String employeeName;

    private LocalDate date;

    private LocalDateTime clockInTime;

    private LocalDateTime clockOutTime;
}