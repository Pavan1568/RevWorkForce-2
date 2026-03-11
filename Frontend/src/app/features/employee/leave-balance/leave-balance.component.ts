import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-leave-balance',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-3">
      <h2 class="mb-3">Leave Balance</h2>
      <p class="text-muted">Leave balance is managed by your manager. Contact them or check My Leaves for your requests.</p>
    </div>
  `
})
export class LeaveBalanceComponent {}
