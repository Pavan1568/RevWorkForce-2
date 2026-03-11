import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from '../../../core/services/employee.service';

@Component({
  selector: 'app-assign-manager',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './assign-manager.component.html'
})
export class AssignManagerComponent implements OnInit {

  employees: any[] = [];
  managers: any[] = [];

  selectedEmployeeId: number | null = null;
  selectedManagerId: number | null = null;

  isSubmitting = false;

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.loadEmployees();
    this.loadManagers(); // ✅ IMPORTANT
  }

  loadEmployees() {
    this.employeeService.getAll().subscribe({
      next: (data) => {
        this.employees = data;
      },
      error: (err) => {
        console.error('Failed to load employees', err);
        alert('Failed to load employees');
      }
    });
  }

  loadManagers() {
    this.employeeService.getManagers().subscribe({
      next: (data) => {
        this.managers = data;
      },
      error: (err) => {
        console.error('Failed to load managers', err);
        alert('Failed to load managers');
      }
    });
  }

  assign() {
    if (!this.selectedEmployeeId || !this.selectedManagerId) {
      alert('Select an employee and a manager');
      return;
    }

    if (this.selectedEmployeeId === this.selectedManagerId) {
      alert('Employee and manager must be different');
      return;
    }

    this.isSubmitting = true;

    this.employeeService
      .assignManager(this.selectedEmployeeId, this.selectedManagerId)
      .subscribe({
        next: () => {
          alert('Manager assigned successfully');
          this.loadEmployees();

          // this.selectedEmployeeId = null;
          // this.selectedManagerId = null;
          // this.isSubmitting = false;

          // this.loadEmployees(); // refresh table
          
        },
        error: (err) => {
          console.error(err);
          alert('Failed to assign manager');
          this.isSubmitting = false;
        }
      });
  }
}