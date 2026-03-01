import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeLeaveService } from '../../../core/services/employee-leave.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class EmployeeDashboardComponent implements OnInit {

  totalAllocated = 0;
  usedLeaves = 0;
  remainingLeaves = 0;

  constructor(private leaveService: EmployeeLeaveService) {}

  ngOnInit(): void {

    const employeeId = Number(localStorage.getItem('employeeId'));
    if (!employeeId) return;

    this.leaveService.getLeaveBalance(employeeId)
      .subscribe((data: any[]) => {

        this.totalAllocated = data.reduce((sum, l) => sum + l.totalAllocated, 0);
        this.usedLeaves = data.reduce((sum, l) => sum + l.usedLeaves, 0);
        this.remainingLeaves = data.reduce((sum, l) => sum + l.remainingLeaves, 0);
      });
  }
}