package com.revworkforce.controller;

import com.revworkforce.entity.LeaveType;
import com.revworkforce.service.LeaveTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/leave-types")
@PreAuthorize("hasRole('ADMIN')")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    public LeaveTypeController(LeaveTypeService leaveTypeService) {
        this.leaveTypeService = leaveTypeService;
    }

    @PostMapping
    public LeaveType createLeaveType(@RequestBody LeaveType leaveType) {
        return leaveTypeService.createLeaveType(leaveType);
    }

    @GetMapping
    public List<LeaveType> getAllLeaveTypes() {
        return leaveTypeService.getAllLeaveTypes();
    }

    @PutMapping("/{id}")
    public LeaveType updateLeaveType(@PathVariable Long id,
                                     @RequestBody LeaveType leaveType) {
        return leaveTypeService.updateLeaveType(id, leaveType);
    }

    @DeleteMapping("/{id}")
    public void deleteLeaveType(@PathVariable Long id) {
        leaveTypeService.deleteLeaveType(id);
    }
}