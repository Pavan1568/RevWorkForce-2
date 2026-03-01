package com.revworkforce.repository;

import com.revworkforce.entity.Employee;
import com.revworkforce.entity.User;
import com.revworkforce.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByUserId(Long userId);

    List<Employee> findByManager_Id(Long managerId);

    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);

    List<Employee> findByUser_EmailContainingIgnoreCase(String email);

    List<Employee> findByDepartment_NameContainingIgnoreCase(String departmentName);

    List<Employee> findByUser_Roles_Name(RoleType roleType);

    Optional<Employee> findByUser(User user);
}