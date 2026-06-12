package com.pragun.hrms.service;

import com.pragun.hrms.dto.response.AttendanceResponse;

import java.util.List;

public interface AttendanceService {

    AttendanceResponse clockIn();

    AttendanceResponse clockOut();

    List<AttendanceResponse> getMyAttendance();
}