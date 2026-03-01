import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ManagerLeaveService } from '../../../core/services/manager-leave.service';

@Component({
  selector: 'app-approve-leaves',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './approve-leaves.component.html'
})
export class ApproveLeavesComponent {

  managerId: number | null = null;
  leaves: any[] = [];
  comment = '';
  processingId: number | null = null;

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

  get pendingLeaves() {
    return this.leaves.filter((l: any) => l.status === 'PENDING');
  }

  approve(leave: any) {
    const comment = prompt('Comment (optional):') || '';
    this.processingId = leave.id;
    this.managerLeaveService.approveLeave(leave.id, comment).subscribe({
      next: () => {
        alert('Leave approved');
        this.loadLeaves();
        this.processingId = null;
      },
      error: (err) => {
        console.error(err);
        alert('Failed to approve');
        this.processingId = null;
      }
    });
  }

  reject(leave: any) {
    const comment = prompt('Comment (optional):') || '';
    this.processingId = leave.id;
    this.managerLeaveService.rejectLeave(leave.id, comment).subscribe({
      next: () => {
        alert('Leave rejected');
        this.loadLeaves();
        this.processingId = null;
      },
      error: (err) => {
        console.error(err);
        alert('Failed to reject');
        this.processingId = null;
      }
    });
  }
}
