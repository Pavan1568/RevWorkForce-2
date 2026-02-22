package com.revworkforce.service.impl;

import com.revworkforce.dto.ProfileResponseDTO;
import com.revworkforce.dto.UpdateProfileDTO;
import com.revworkforce.entity.Employee;
import com.revworkforce.repository.EmployeeRepository;
import com.revworkforce.service.EmployeeService;
import org.springframework.stereotype.Service;
import com.revworkforce.dto.ManagerResponseDTO;
import com.revworkforce.exception.ResourceNotFoundException;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // ================= GET PROFILE =================

    @Override
    public ProfileResponseDTO getProfile(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getUser().getEmail());
        dto.setPhone(employee.getPhone());
        dto.setAddress(employee.getAddress());
        dto.setEmergencyContact(employee.getEmergencyContact());

        if (employee.getManager() != null) {
            dto.setManagerName(employee.getManager().getName());
        }

        return dto;
    }

    // ================= UPDATE PROFILE =================

    @Override
    public ProfileResponseDTO updateProfile(Long employeeId, UpdateProfileDTO dto) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setEmergencyContact(dto.getEmergencyContact());

        employeeRepository.save(employee);

        return getProfile(employeeId);
    }

    @Override
    public ManagerResponseDTO getReportingManager(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (employee.getManager() == null) {
            throw new ResourceNotFoundException("Reporting manager not assigned");
        }

        Employee manager = employee.getManager();

        return new ManagerResponseDTO(
                manager.getId(),
                manager.getFirstName() + " " + manager.getLastName(),
                manager.getUser().getEmail(),
                manager.getDepartment().getName(),
                manager.getDesignation().getName()
        );
    }
}