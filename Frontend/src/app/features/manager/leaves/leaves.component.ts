import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ManagerLeaveService } from '../../../core/services/manager-leave.service';

@Component({
  selector: 'app-manager-leaves',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './leaves.component.html'
})
export class LeavesComponent {

  managerId: number | null = null;
  leaves: any[] = [];

  constructor(private managerLeaveService: ManagerLeaveService) {}

  loadLeaves() {
    if (!this.managerId) {
      alert('Please enter Manager ID');
      return;
    }
    this.managerLeaveService.getTeamLeaves(this.managerId).subscribe({
      next: (data) => {
        this.leaves = data;
      },
      error: (err) => {
        console.error('Failed to load leaves', err);
        alert('Failed to load leaves');
      }
    });
  }
}
