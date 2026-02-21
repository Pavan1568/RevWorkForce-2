package com.revworkforce.service.impl;

import com.revworkforce.entity.LeaveType;
import com.revworkforce.repository.LeaveTypeRepository;
import com.revworkforce.service.LeaveTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeServiceImpl(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    @Override
    public LeaveType createLeaveType(LeaveType leaveType) {
        return leaveTypeRepository.save(leaveType);
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
    public void deleteLeaveType(Long id) {
        leaveTypeRepository.deleteById(id);
    }
}