package com.revworkforce.service.impl;

import com.revworkforce.entity.Employee;
import com.revworkforce.entity.Goal;
import com.revworkforce.exception.ResourceNotFoundException;
import com.revworkforce.repository.EmployeeRepository;
import com.revworkforce.repository.GoalRepository;
import com.revworkforce.service.GoalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final EmployeeRepository employeeRepository;

    public GoalServiceImpl(GoalRepository goalRepository,
                           EmployeeRepository employeeRepository) {
        this.goalRepository = goalRepository;
        this.employeeRepository = employeeRepository;
    }

    // ✅ Create Goal
    @Override
    public Goal createGoal(Long employeeId, Goal goal) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        goal.setEmployee(employee);
        goal.setStatus("ACTIVE");
        goal.setProgress(0);

        return goalRepository.save(goal);
    }

    // ✅ Update Goal Details
    @Override
    public Goal updateGoal(Long goalId, Goal updatedGoal) {

        Goal existing = goalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        existing.setTitle(updatedGoal.getTitle());
        existing.setDescription(updatedGoal.getDescription());
        existing.setDeadline(updatedGoal.getDeadline());
        existing.setYear(updatedGoal.getYear());

        return goalRepository.save(existing);
    }

    // ✅ Update Progress
    @Override
    public Goal updateProgress(Long goalId, Integer progress) {

        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Progress must be between 0 and 100");
        }

        Goal existing = goalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        existing.setProgress(progress);

        if (progress == 100) {
            existing.setStatus("COMPLETED");
        }

        return goalRepository.save(existing);
    }

    // ✅ Get All Goals of Employee
    @Override
    public List<Goal> getGoalsByEmployee(Long employeeId) {
        return goalRepository.findByEmployeeId(employeeId);
    }

    // ✅ Get Goals by Year
    @Override
    public List<Goal> getGoalsByEmployeeAndYear(Long employeeId, Integer year) {
        return goalRepository.findByEmployeeIdAndYear(employeeId, year);
    }

    // ✅ Delete Goal
    @Override
    public void deleteGoal(Long goalId) {
        goalRepository.deleteById(goalId);
    }
}