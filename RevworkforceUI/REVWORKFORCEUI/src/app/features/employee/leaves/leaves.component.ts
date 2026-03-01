import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeLeaveService } from '../../../core/services/employee-leave.service';
import { LeaveTypeService } from '../../../core/services/leave-type.service';

@Component({
  selector: 'app-leaves',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './leaves.component.html'
})
export class LeavesComponent implements OnInit {

  employeeId: number | null = null;
  leaveTypes: any[] = [];
  myLeaves: any[] = [];

  newLeave: any = {
    leaveTypeId: null,
    startDate: '',
    endDate: '',
    reason: ''
  };

  isSubmitting = false;

  constructor(
    private employeeLeaveService: EmployeeLeaveService,
    private leaveTypeService: LeaveTypeService
  ) {}

  ngOnInit(): void {
     this.employeeId = Number(localStorage.getItem('employeeId'));
     this.loadLeaveTypes();
     this.loadMyLeaves();
  }

  loadLeaveTypes() {
    this.leaveTypeService.getAll().subscribe({
      next: (data) => {
        this.leaveTypes = data;
      },
      error: (err) => {
        console.error('Failed to load leave types', err);
        alert('Failed to load leave types');
      }
    });
  }

  loadMyLeaves() {
    if (!this.employeeId) {
      alert('Please enter your Employee ID');
      return;
    }

    this.employeeLeaveService.getMyLeaves(this.employeeId).subscribe({
      next: (data) => {
        this.myLeaves = data;
      },
      error: (err) => {
        console.error('Failed to load leaves', err);
        alert('Failed to load leaves');
      }
    });
  }

  applyLeave() {
    if (!this.employeeId) {
      alert('Please enter your Employee ID');
      return;
    }

    if (!this.newLeave.leaveTypeId || !this.newLeave.startDate || !this.newLeave.endDate || !this.newLeave.reason) {
      alert('Leave type, date range and reason are required');
      return;
    }

    const requestPayload = {
      employeeId: this.employeeId,
      leaveTypeId: this.newLeave.leaveTypeId,
      startDate: this.newLeave.startDate,
      endDate: this.newLeave.endDate,
      reason: this.newLeave.reason
    };

    this.isSubmitting = true;

    this.employeeLeaveService.applyLeave(requestPayload).subscribe({
      next: () => {
        alert('Leave applied successfully');
        this.newLeave = {
          leaveTypeId: null,
          startDate: '',
          endDate: '',
          reason: ''
        };
        this.loadMyLeaves();
        this.isSubmitting = false;
      },
      error: (err) => {
        console.error('Failed to apply leave', err);
        alert('Failed to apply leave');
        this.isSubmitting = false;
      }
    });
  }
}
