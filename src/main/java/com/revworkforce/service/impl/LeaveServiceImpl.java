package com.revworkforce.service.impl;

import com.revworkforce.entity.*;
import com.revworkforce.enums.LeaveStatus;
import com.revworkforce.exception.ResourceNotFoundException;
import com.revworkforce.repository.*;
import com.revworkforce.service.LeaveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LeaveServiceImpl implements LeaveService {

    private final LeaveApplicationRepository leaveApplicationRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveServiceImpl(
            LeaveApplicationRepository leaveApplicationRepository,
            LeaveBalanceRepository leaveBalanceRepository,
            LeaveTypeRepository leaveTypeRepository,
            EmployeeRepository employeeRepository
    ) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveTypeRepository = leaveTypeRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public LeaveApplication applyLeave(
            Long employeeId,
            Long leaveTypeId,
            LocalDate startDate,
            LocalDate endDate,
            String reason
    ) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LeaveType leaveType = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));

        LeaveBalance leaveBalance = leaveBalanceRepository
                .findByEmployeeAndLeaveType(employee, leaveType)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not configured"));

        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        if (leaveBalance.getRemainingLeaves() < numberOfDays) {
            throw new ResourceNotFoundException("Insufficient leave balance");
        }

        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setEmployee(employee);
        leaveApplication.setLeaveType(leaveType);
        leaveApplication.setStartDate(startDate);
        leaveApplication.setEndDate(endDate);
        leaveApplication.setReason(reason);
        leaveApplication.setStatus(LeaveStatus.PENDING);

        return leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    public LeaveApplication approveLeave(Long leaveApplicationId, String managerComment) {

        LeaveApplication leaveApplication = leaveApplicationRepository.findById(leaveApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave application not found"));

        leaveApplication.setStatus(LeaveStatus.APPROVED);
        leaveApplication.setManagerComments(managerComment);

        LeaveBalance balance = leaveBalanceRepository
                .findByEmployeeAndLeaveType(
                        leaveApplication.getEmployee(),
                        leaveApplication.getLeaveType()
                ).orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));

        long days = ChronoUnit.DAYS.between(
                leaveApplication.getStartDate(),
                leaveApplication.getEndDate()
        ) + 1;

        balance.setUsedLeaves(balance.getUsedLeaves() + (int) days);
        balance.setRemainingLeaves(balance.getRemainingLeaves() - (int) days);

        leaveBalanceRepository.save(balance);

        return leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    public LeaveApplication rejectLeave(Long leaveApplicationId, String managerComment) {

        LeaveApplication leaveApplication = leaveApplicationRepository.findById(leaveApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave application not found"));

        leaveApplication.setStatus(LeaveStatus.REJECTED);
        leaveApplication.setManagerComments(managerComment);

        return leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    public LeaveApplication cancelLeave(Long leaveApplicationId) {

        LeaveApplication leaveApplication = leaveApplicationRepository.findById(leaveApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave application not found"));

        leaveApplication.setStatus(LeaveStatus.CANCELLED);

        return leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    public List<LeaveApplication> getEmployeeLeaves(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return leaveApplicationRepository.findByEmployee(employee);
    }

    @Override
    public List<LeaveApplication> getManagerTeamLeaves(Long managerId) {
        return leaveApplicationRepository.findByEmployee_Manager_Id(managerId);
    }
}