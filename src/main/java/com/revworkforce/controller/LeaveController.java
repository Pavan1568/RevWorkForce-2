package com.revworkforce.controller;

import com.revworkforce.entity.LeaveApplication;
import com.revworkforce.service.LeaveService;
import org.springframework.web.bind.annotation.*;
import com.revworkforce.dto.ApplyLeaveRequestDTO;
import com.revworkforce.dto.LeaveResponseDTO;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    // 1️⃣ Apply Leave
    @PostMapping("/apply")
    public LeaveResponseDTO applyLeave(
            @Valid @RequestBody ApplyLeaveRequestDTO request
    ) {

        LeaveApplication leaveApplication = leaveService.applyLeave(
                request.getEmployeeId(),
                request.getLeaveTypeId(),
                request.getStartDate(),
                request.getEndDate(),
                request.getReason()
        );

        LeaveResponseDTO response = new LeaveResponseDTO();
        response.setId(leaveApplication.getId());
        response.setEmployeeId(leaveApplication.getEmployee().getId());
        response.setLeaveTypeId(leaveApplication.getLeaveType().getId());
        response.setStartDate(leaveApplication.getStartDate());
        response.setEndDate(leaveApplication.getEndDate());
        response.setReason(leaveApplication.getReason());
        response.setStatus(leaveApplication.getStatus().name());
        response.setManagerComments(leaveApplication.getManagerComments());

        return response;
    }

    // 2️⃣ Approve Leave
    @PutMapping("/{id}/approve")
    public LeaveApplication approveLeave(
            @PathVariable Long id,
            @RequestParam String comment
    ) {
        return leaveService.approveLeave(id, comment);
    }

    // 3️⃣ Reject Leave
    @PutMapping("/{id}/reject")
    public LeaveApplication rejectLeave(
            @PathVariable Long id,
            @RequestParam String comment
    ) {
        return leaveService.rejectLeave(id, comment);
    }

    // 4️⃣ Cancel Leave
    @PutMapping("/{id}/cancel")
    public LeaveApplication cancelLeave(@PathVariable Long id) {
        return leaveService.cancelLeave(id);
    }

    // 5️⃣ View Employee Leaves
    @GetMapping("/employee/{employeeId}")
    public List<LeaveApplication> getEmployeeLeaves(@PathVariable Long employeeId) {
        return leaveService.getEmployeeLeaves(employeeId);
    }

    // 6️⃣ View Manager Team Leaves
    @GetMapping("/manager/{managerId}")
    public List<LeaveApplication> getManagerTeamLeaves(@PathVariable Long managerId) {
        return leaveService.getManagerTeamLeaves(managerId);
    }
}