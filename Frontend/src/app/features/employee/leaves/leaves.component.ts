import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs';

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

  showApplyForm = false;

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
    private router: Router
  ) {}

  ngOnInit(): void {

    const id = localStorage.getItem('employeeId');
    if (id) {
      this.employeeId = Number(id);
    }

    this.loadLeaveTypes();
    this.detectRoute();

    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.detectRoute();
      });
  }

  detectRoute() {

    const url = this.router.url;

    if (url.includes('apply-leave')) {
      this.showApplyForm = true;
    } else {
      this.showApplyForm = false;
      this.loadMyLeaves();
    }
  }

  loadLeaveTypes() {
    this.leaveTypeService.getAll().subscribe({
      next: (data) => {
        this.leaveTypes = data;
      }
    });
  }

  loadMyLeaves() {

    if (!this.employeeId) return;

    this.employeeLeaveService.getMyLeaves(this.employeeId).subscribe({
      next: (data) => {
        this.myLeaves = data;
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

        alert('Leave Applied Successfully');

        this.newLeave = {
          leaveTypeId: null,
          startDate: '',
          endDate: '',
          reason: ''
        };

        this.isSubmitting = false;

        this.loadMyLeaves();
      },
      error: () => {
        this.isSubmitting = false;
      }
    });
  }

}