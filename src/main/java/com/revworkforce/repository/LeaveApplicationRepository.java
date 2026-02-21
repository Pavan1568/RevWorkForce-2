package com.revworkforce.repository;

import com.revworkforce.entity.LeaveApplication;
import com.revworkforce.entity.Employee;
import com.revworkforce.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

    List<LeaveApplication> findByEmployee(Employee employee);

    List<LeaveApplication> findByEmployee_Manager_Id(Long managerId);

    List<LeaveApplication> findByStatus(LeaveStatus status);
}