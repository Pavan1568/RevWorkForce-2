package com.revworkforce.service;

import com.revworkforce.dto.ProfileResponseDTO;
import com.revworkforce.dto.UpdateProfileDTO;

public interface EmployeeService {

    ProfileResponseDTO getProfile(Long employeeId);

    ProfileResponseDTO updateProfile(Long employeeId, UpdateProfileDTO dto);
}