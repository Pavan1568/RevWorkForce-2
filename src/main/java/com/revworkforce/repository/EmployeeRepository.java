package com.revworkforce.repository;

import com.revworkforce.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByManager_Id(Long managerId);

    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);

    List<Employee> findByUser_EmailContainingIgnoreCase(String email);

    List<Employee> findByDepartment_NameContainingIgnoreCase(String departmentName);
}