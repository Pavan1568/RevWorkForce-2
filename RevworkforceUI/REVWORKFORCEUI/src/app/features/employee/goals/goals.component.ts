import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GoalService } from '../../../core/services/goal.service';

@Component({
  selector: 'app-goals',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './goals.component.html'
})
export class GoalsComponent implements OnInit {

  employeeId: number | null = null;
  goals: any[] = [];
  newGoal: any = {
    title: '',
    description: '',
    year: new Date().getFullYear(),
    deadline: '',
    status: 'ACTIVE'
  };
  isSubmitting = false;
  editingProgressGoalId: number | null = null;
  editingProgressValue = 0;

  constructor(private goalService: GoalService) {}

  ngOnInit(): void {}

  loadGoals() {
    if (!this.employeeId) {
      alert('Please enter your Employee ID');
      return;
    }
    this.goalService.getGoals(this.employeeId).subscribe({
      next: (data) => {
        this.goals = data;
      },
      error: (err) => {
        console.error('Failed to load goals', err);
        alert('Failed to load goals');
      }
    });
  }

  createGoal() {
    if (!this.employeeId) {
      alert('Please enter your Employee ID');
      return;
    }
    if (!this.newGoal.title) {
      alert('Goal title is required');
      return;
    }
    this.isSubmitting = true;
    const payload = {
      title: this.newGoal.title,
      description: this.newGoal.description || null,
      year: this.newGoal.year,
      deadline: this.newGoal.deadline || null,
      status: this.newGoal.status || 'ACTIVE'
    };
    this.goalService.create(this.employeeId, payload).subscribe({
      next: () => {
        alert('Goal created successfully');
        this.newGoal = { title: '', description: '', year: new Date().getFullYear(), deadline: '', status: 'ACTIVE' };
        this.loadGoals();
        this.isSubmitting = false;
      },
      error: (err) => {
        console.error('Failed to create goal', err);
        alert('Failed to create goal');
        this.isSubmitting = false;
      }
    });
  }

  startEditProgress(goal: any) {
    this.editingProgressGoalId = goal.id;
    this.editingProgressValue = goal.progress ?? 0;
  }

  saveProgress() {
    if (this.editingProgressGoalId == null) return;
    this.goalService.updateProgress(this.editingProgressGoalId, this.editingProgressValue).subscribe({
      next: () => {
        this.editingProgressGoalId = null;
        if (this.employeeId) this.loadGoals();
      },
      error: (err) => {
        console.error('Failed to update progress', err);
        alert('Failed to update progress');
      }
    });
  }

  cancelEditProgress() {
    this.editingProgressGoalId = null;
  }
}
