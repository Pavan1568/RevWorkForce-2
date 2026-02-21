package com.revworkforce.controller;

import com.revworkforce.entity.LeaveApplication;
import com.revworkforce.service.LeaveService;
import org.springframework.web.bind.annotation.*;

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
    public LeaveApplication applyLeave(
            @RequestParam Long employeeId,
            @RequestParam Long leaveTypeId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String reason
    ) {
        return leaveService.applyLeave(
                employeeId,
                leaveTypeId,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate),
                reason
        );
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