package com.revworkforce.controller;
import com.revworkforce.dto.CreateEmployeeRequest;
import com.revworkforce.dto.EmployeeResponseDTO;
import com.revworkforce.dto.ManagerResponseDTO;
import com.revworkforce.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.revworkforce.entity.Employee;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ✅ Get Reporting Manager
    @GetMapping("/{id}/manager")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public ManagerResponseDTO getReportingManager(@PathVariable Long id) {
        return employeeService.getReportingManager(id);
    }

    @PutMapping("/{employeeId}/assign-manager/{managerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void assignManager(@PathVariable Long employeeId,
                              @PathVariable Long managerId) {
        employeeService.assignManager(employeeId, managerId);
    }

    // 🔍 Search by name
    @GetMapping("/search/name")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> searchByName(@RequestParam String value) {
        return employeeService.searchByName(value);
    }

    // 🔍 Search by email
    @GetMapping("/search/email")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> searchByEmail(@RequestParam String value) {
        return employeeService.searchByEmail(value);
    }

    // 🔍 Search by department
    @GetMapping("/search/department")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> searchByDepartment(@RequestParam String value) {
        return employeeService.searchByDepartment(value);
    }

    @PostMapping("/assign-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createEmployeeForUser(
            @PathVariable Long userId,
            @RequestBody CreateEmployeeRequest request) {

        employeeService.createEmployeeForUser(userId, request);
        return ResponseEntity.ok("Employee created successfully");
    }

    @GetMapping
    public List<EmployeeResponseDTO> getAll() {

        return employeeService.getAllEmployees()
                .stream()
                .map(emp -> new EmployeeResponseDTO(
                        emp.getId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getUser().getEmail(),
                        emp.getDepartment() != null ? emp.getDepartment().getName() : null,
                        emp.getManager() != null
                                ? emp.getManager().getFirstName() + " " + emp.getManager().getLastName()
                                : null
                ))
                .toList();
    }
}