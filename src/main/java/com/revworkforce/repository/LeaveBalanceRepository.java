package com.revworkforce.repository;

import com.revworkforce.entity.LeaveBalance;
import com.revworkforce.entity.Employee;
import com.revworkforce.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

    Optional<LeaveBalance> findByEmployeeAndLeaveType(Employee employee, LeaveType leaveType);

    List<LeaveBalance> findByEmployee(Employee employee);
}