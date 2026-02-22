package com.revworkforce.service;
import com.revworkforce.dto.ManagerResponseDTO;
import com.revworkforce.dto.ProfileResponseDTO;
import com.revworkforce.dto.UpdateProfileDTO;
import java.util.List;
import com.revworkforce.entity.Employee;

public interface EmployeeService {

    ProfileResponseDTO getProfile(Long employeeId);

    ProfileResponseDTO updateProfile(Long employeeId, UpdateProfileDTO dto);

    ManagerResponseDTO getReportingManager(Long employeeId);

    void assignManager(Long employeeId, Long managerId);

    List<Employee> searchByName(String name);

    List<Employee> searchByEmail(String email);

    List<Employee> searchByDepartment(String department);
}