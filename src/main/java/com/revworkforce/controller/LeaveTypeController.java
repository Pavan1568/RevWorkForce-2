package com.revworkforce.controller;

import com.revworkforce.entity.LeaveType;
import com.revworkforce.service.LeaveTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-types")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    public LeaveTypeController(LeaveTypeService leaveTypeService) {
        this.leaveTypeService = leaveTypeService;
    }

    // ✅ Accessible to all authenticated users
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    public List<LeaveType> getAllLeaveTypes() {
        return leaveTypeService.getAllLeaveTypes();
    }

    // 🔐 Admin-only operations
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public LeaveType createLeaveType(@RequestBody LeaveType leaveType) {
        return leaveTypeService.createLeaveType(leaveType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public LeaveType updateLeaveType(@PathVariable Long id,
                                     @RequestBody LeaveType leaveType) {
        return leaveTypeService.updateLeaveType(id, leaveType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteLeaveType(@PathVariable Long id) {
        leaveTypeService.deleteLeaveType(id);
    }
}