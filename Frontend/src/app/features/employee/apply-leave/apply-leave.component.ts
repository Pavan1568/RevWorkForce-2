import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeLeaveService } from '../../../core/services/employee-leave.service';
import { LeaveTypeService } from '../../../core/services/leave-type.service';

@Component({
  selector: 'app-apply-leave',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './apply-leave.component.html'
})
export class ApplyLeaveComponent implements OnInit {

  employeeId: number | null = null;

  leaveTypes: any[] = [];

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

    const id = localStorage.getItem('employeeId');
    if (id) this.employeeId = Number(id);

    this.leaveTypeService.getAll().subscribe(data => {
      this.leaveTypes = data;
    });

  }

  applyLeave() {

    if (!this.employeeId) return;

    const payload = {
      employeeId: this.employeeId,
      leaveTypeId: this.newLeave.leaveTypeId,
      startDate: this.newLeave.startDate,
      endDate: this.newLeave.endDate,
      reason: this.newLeave.reason
    };

    this.isSubmitting = true;

    this.employeeLeaveService.applyLeave(payload).subscribe({
      next: () => {
        alert("Leave applied successfully");
        this.isSubmitting = false;
      },
      error: () => {
        this.isSubmitting = false;
      }
    });

  }

}