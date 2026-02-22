package com.revworkforce.controller;

import com.revworkforce.entity.Goal;
import com.revworkforce.service.GoalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    // ✅ Create Goal
    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Goal createGoal(@RequestParam Long employeeId,
                           @RequestBody Goal goal) {

        return goalService.createGoal(employeeId, goal);
    }

    // ✅ Update Goal Details
    @PutMapping("/{goalId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Goal updateGoal(@PathVariable Long goalId,
                           @RequestBody Goal goal) {

        return goalService.updateGoal(goalId, goal);
    }

    // ✅ Update Progress Only
    @PatchMapping("/{goalId}/progress")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Goal updateProgress(@PathVariable Long goalId,
                               @RequestParam Integer progress) {

        return goalService.updateProgress(goalId, progress);
    }

    // ✅ Get All Goals
    @GetMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<Goal> getGoals(@RequestParam Long employeeId) {

        return goalService.getGoalsByEmployee(employeeId);
    }

    // ✅ Get Goals By Year
    @GetMapping("/year/{year}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<Goal> getGoalsByYear(@RequestParam Long employeeId,
                                     @PathVariable Integer year) {

        return goalService.getGoalsByEmployeeAndYear(employeeId, year);
    }

    // ✅ Delete Goal
    @DeleteMapping("/{goalId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public void deleteGoal(@PathVariable Long goalId) {

        goalService.deleteGoal(goalId);
    }
}