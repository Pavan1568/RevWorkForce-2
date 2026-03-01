package com.revworkforce.service;
import com.revworkforce.dto.*;

import java.util.List;
import com.revworkforce.entity.Employee;

public interface EmployeeService {

    Employee createEmployeeForUser(Long userId, CreateEmployeeRequest request);

    ProfileResponseDTO getProfile(Long employeeId);

    ProfileResponseDTO updateProfile(Long employeeId, UpdateProfileDTO dto);

    ManagerResponseDTO getReportingManager(Long employeeId);

    void assignManager(Long employeeId, Long managerId);

    List<Employee> searchByName(String name);

    List<Employee> searchByEmail(String email);

    List<Employee> searchByDepartment(String department);

    List<Employee> getAllEmployees();

    List<Employee> getAllManagers();

//    List<Employee> findByManager_Id(Long managerId);

    List<Employee> getTeamMembers(Long managerId);


}