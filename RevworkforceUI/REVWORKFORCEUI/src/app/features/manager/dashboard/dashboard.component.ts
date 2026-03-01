import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeService } from '../../../core/services/employee.service';
import { EmployeeLeaveService } from '../../../core/services/employee-leave.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-manager-dashboard',
  standalone: true,
 imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class ManagerDashboardComponent implements OnInit {

  totalTeamMembers: number = 0;
  pendingLeaves: number = 0;
  onLeaveToday: number = 0;

  constructor(
    private employeeService: EmployeeService,
    private leaveService: EmployeeLeaveService
  ) {}

  ngOnInit(): void {

    const managerId = Number(localStorage.getItem('employeeId'));

    if (!managerId) return;

    // Team count
    this.employeeService.getTeamMembers(managerId)
      .subscribe((data: any[]) => {
        this.totalTeamMembers = data.length;
      });

    // Pending leaves (manager view)
    this.leaveService.getTeamLeaves(managerId)
      .subscribe((data: any[]) => {
        this.pendingLeaves = data.filter(
          l => l.status === 'PENDING'
        ).length;
      });
  }
}