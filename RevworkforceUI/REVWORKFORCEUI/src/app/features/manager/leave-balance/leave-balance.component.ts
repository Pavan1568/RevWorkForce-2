import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ManagerLeaveService } from '../../../core/services/manager-leave.service';

@Component({
  selector: 'app-leave-balance',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './leave-balance.component.html'
})
export class LeaveBalanceComponent {

  employeeId: number | null = null;
  balances: any[] = [];

  constructor(private managerLeaveService: ManagerLeaveService) {}

  loadBalance() {
    if (!this.employeeId) {
      alert('Please enter Employee ID');
      return;
    }
    this.managerLeaveService.getEmployeeLeaveBalance(this.employeeId).subscribe({
      next: (data) => {
        this.balances = data;
      },
      error: (err) => {
        console.error('Failed to load balance', err);
        alert('Failed to load leave balance');
      }
    });
  }
}
