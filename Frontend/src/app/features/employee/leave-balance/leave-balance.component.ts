import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeLeaveService } from '../../../core/services/employee-leave.service';

@Component({
  selector: 'app-leave-balance',
  standalone: true,
  imports: [CommonModule],
  template: `
  <div class="container mt-3">

    <h2 class="mb-3">My Leave Balance</h2>

    <div class="table-responsive">

      <table class="table table-striped table-bordered table-sm">

        <thead class="table-light">
          <tr>
            <th>Leave Type</th>
            <th>Total</th>
            <th>Used</th>
            <th>Remaining</th>
          </tr>
        </thead>

        <tbody>

          <tr *ngFor="let b of balances">
            <td>{{ b.leaveType?.name }}</td>
            <td>{{ b.totalAllocated }}</td>
            <td>{{ b.usedLeaves }}</td>
            <td>{{ b.remainingLeaves }}</td>
          </tr>

          <tr *ngIf="balances.length === 0">
            <td colspan="4" class="text-center text-muted">
              No leave balance available
            </td>
          </tr>

        </tbody>

      </table>

    </div>

  </div>
  `
})
export class LeaveBalanceComponent implements OnInit {

  balances: any[] = [];

  constructor(private employeeLeaveService: EmployeeLeaveService) {}

  ngOnInit(): void {

    const employeeId = Number(localStorage.getItem("employeeId"));

    this.employeeLeaveService.getLeaveBalance(employeeId).subscribe({
      next: (data) => {
        this.balances = data;
      },
      error: () => {
        console.error("Failed to load leave balance");
      }
    });

  }

}