package com.revworkforce.service;

import com.revworkforce.entity.LeaveApplication;

import java.time.LocalDate;
import java.util.List;

public interface LeaveService {

    LeaveApplication applyLeave(
            Long employeeId,
            Long leaveTypeId,
            LocalDate startDate,
            LocalDate endDate,
            String reason
    );

    LeaveApplication approveLeave(Long leaveApplicationId, String managerComment);

    LeaveApplication rejectLeave(Long leaveApplicationId, String managerComment);

    LeaveApplication cancelLeave(Long leaveApplicationId);

    List<LeaveApplication> getEmployeeLeaves(Long employeeId);

    List<LeaveApplication> getManagerTeamLeaves(Long managerId);

    List<LeaveApplication> getAllLeaves();
}