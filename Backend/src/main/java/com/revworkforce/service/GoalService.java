package com.revworkforce.service;

import com.revworkforce.entity.Goal;

import java.util.List;

public interface GoalService {

    Goal createGoal(Long employeeId, Goal goal);

    Goal updateGoal(Long goalId, Goal updatedGoal);

    Goal updateProgress(Long goalId, Integer progress);

    List<Goal> getGoalsByEmployee(Long employeeId);

    List<Goal> getGoalsByEmployeeAndYear(Long employeeId, Integer year);

    void deleteGoal(Long goalId);
}