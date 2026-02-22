package com.revworkforce.controller;

import com.revworkforce.dto.ManagerResponseDTO;
import com.revworkforce.service.EmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ✅ Get Reporting Manager
    @GetMapping("/{id}/manager")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ManagerResponseDTO getReportingManager(@PathVariable Long id) {
        return employeeService.getReportingManager(id);
    }
}