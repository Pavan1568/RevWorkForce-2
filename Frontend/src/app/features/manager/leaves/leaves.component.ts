import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { EmployeeLeaveService } from '../../../core/services/employee-leave.service';
import { LeaveTypeService } from '../../../core/services/leave-type.service';
import { ManagerLeaveService } from '../../../core/services/manager-leave.service';

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
  

  showApplyForm: boolean = true;

  newLeave: any = {
    leaveTypeId: null,
    startDate: '',
    endDate: '',
    reason: ''
  };

  isSubmitting = false;

  constructor(
    private employeeLeaveService: EmployeeLeaveService,
    private leaveTypeService: LeaveTypeService,
    private managerLeaveService: ManagerLeaveService,
    private router: Router
  ) {}

  ngOnInit(): void {

    const id = localStorage.getItem('employeeId');
    if (id) {
      this.employeeId = Number(id);
    }

    if (this.router.url.includes("apply-leave")) {
      this.showApplyForm = true;
    } else {
  this.showApplyForm = false;

  if (this.router.url.includes("team-leaves")) {
    this.loadTeamCalendar();
  } else {
    this.loadMyLeaves();
  }
}

    this.loadLeaveTypes();
  }

  loadLeaveTypes() {
    this.leaveTypeService.getAll().subscribe({
      next: (data) => {
        this.leaveTypes = data;
      },
      error: (err) => {
        console.error('Failed to load leave types', err);
      }
    });
  }

  loadMyLeaves() {

    if (!this.employeeId) return;

    this.employeeLeaveService.getMyLeaves(this.employeeId).subscribe({
      next: (data) => {
        this.myLeaves = data;
      },
      error: (err) => {
        console.error('Failed to load leaves', err);
      }
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

        alert("Leave Applied Successfully");

        this.newLeave = {
          leaveTypeId: null,
          startDate: '',
          endDate: '',
          reason: ''
        };

        this.isSubmitting = false;

        this.loadMyLeaves();
      },
      error: (err) => {
        console.error("Failed to apply leave", err);
        this.isSubmitting = false;
      }
    });

  }


  loadTeamCalendar() {

  const managerId = localStorage.getItem("employeeId");

  if (!managerId) return;

  this.managerLeaveService.getTeamLeaveCalendar(Number(managerId))
    .subscribe({
      next: (data:any) => {
        this.myLeaves = data;
      },
      error: (err:any) => {
        console.error("Failed to load calendar", err);
      }
    });

}
}