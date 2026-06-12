package com.pragun.hrms.service;

import com.pragun.hrms.dto.request.LeaveDecisionRequest;
import com.pragun.hrms.dto.request.LeaveRequest;
import com.pragun.hrms.dto.response.LeaveResponse;

import java.util.List;

public interface LeaveService {

    LeaveResponse applyLeave(LeaveRequest request);

    List<LeaveResponse> getMyLeaves();

    List<LeaveResponse> getPendingLeaves();

    LeaveResponse approveLeave(
            Long leaveId,
            LeaveDecisionRequest request);

    LeaveResponse rejectLeave(
            Long leaveId,
            LeaveDecisionRequest request);
}