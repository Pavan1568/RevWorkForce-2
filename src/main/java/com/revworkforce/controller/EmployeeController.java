package com.revworkforce.controller;

import com.revworkforce.dto.EmployeeResponseDTO;
import com.revworkforce.entity.Employee;
import com.revworkforce.repository.EmployeeRepository;
import com.revworkforce.service.EmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService,
                              EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count")
    public long getEmployeeCount() {
        return employeeRepository.count();
    }


    @GetMapping("/managers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getManagers() {
        return employeeRepository.findByUser_Roles_Name(
                com.revworkforce.enums.RoleType.ROLE_MANAGER);
    }

    @GetMapping("/manager/{managerId}/team")
    @PreAuthorize("hasRole('MANAGER')")
    public List<Employee> getTeamMembers(@PathVariable Long managerId) {
        return employeeService.getTeamMembers(managerId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<EmployeeResponseDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(emp -> new EmployeeResponseDTO(
                        emp.getId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getUser().getEmail(),
                        emp.getDepartment() != null
                                ? emp.getDepartment().getName()
                                : null,
                        emp.getManager() != null
                                ? emp.getManager().getFirstName() + " " + emp.getManager().getLastName()
                                : null
                ))
                .toList();
    }
}