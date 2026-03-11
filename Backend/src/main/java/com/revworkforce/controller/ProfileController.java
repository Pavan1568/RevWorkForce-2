package com.revworkforce.controller;

import com.revworkforce.dto.ProfileResponseDTO;
import com.revworkforce.dto.UpdateProfileDTO;
import com.revworkforce.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ProfileResponseDTO getProfile(@PathVariable Long employeeId) {
        return employeeService.getProfile(employeeId);
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ProfileResponseDTO updateProfile(
            @PathVariable Long employeeId,
            @RequestBody UpdateProfileDTO dto) {

        return employeeService.updateProfile(employeeId, dto);
    }
}