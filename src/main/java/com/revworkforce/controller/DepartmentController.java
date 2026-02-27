package com.revworkforce.controller;

import com.revworkforce.dto.DepartmentResponseDTO;
import com.revworkforce.entity.Department;
import com.revworkforce.service.DepartmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/departments")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    @GetMapping
    public List<DepartmentResponseDTO> getAll() {

        return departmentService.getAllDepartments()
                .stream()
                .map(d -> new DepartmentResponseDTO(
                        d.getId(),
                        d.getName(),
                        d.getDescription()
                ))
                .toList();
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id,
                                       @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}