package com.revworkforce.service.impl;
import org.springframework.transaction.annotation.Transactional;
import com.revworkforce.entity.Employee;
import com.revworkforce.entity.LeaveBalance;
import com.revworkforce.entity.LeaveType;
import com.revworkforce.repository.EmployeeRepository;
import com.revworkforce.repository.LeaveApplicationRepository;
import com.revworkforce.repository.LeaveBalanceRepository;
import com.revworkforce.repository.LeaveTypeRepository;
import com.revworkforce.service.LeaveTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveApplicationRepository leaveApplicationRepository;

    public LeaveTypeServiceImpl(LeaveTypeRepository leaveTypeRepository,
                                EmployeeRepository employeeRepository,
                                LeaveBalanceRepository leaveBalanceRepository,
                                LeaveApplicationRepository leaveApplicationRepository) {

        this.leaveTypeRepository = leaveTypeRepository;
        this.employeeRepository = employeeRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    @Override
    public LeaveType createLeaveType(LeaveType leaveType) {

        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {

            LeaveBalance leaveBalance = new LeaveBalance();

            leaveBalance.setEmployee(employee);
            leaveBalance.setLeaveType(savedLeaveType);

            int total = savedLeaveType.getTotalDays();

            leaveBalance.setTotalAllocated(total);
            leaveBalance.setUsedLeaves(0);
            leaveBalance.setRemainingLeaves(total);

            leaveBalanceRepository.save(leaveBalance);
        }

        return savedLeaveType;
    }

    @Override
    public List<LeaveType> getAllLeaveTypes() {
        return leaveTypeRepository.findAll();
    }

    @Override
    public LeaveType updateLeaveType(Long id, LeaveType leaveType) {

        LeaveType existing = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LeaveType not found"));

        existing.setName(leaveType.getName());
        existing.setTotalDays(leaveType.getTotalDays());

        return leaveTypeRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteLeaveType(Long id) {

        leaveApplicationRepository.deleteByLeaveType_Id(id);

        leaveBalanceRepository.deleteByLeaveType_Id(id);

        leaveTypeRepository.deleteById(id);
    }


}
