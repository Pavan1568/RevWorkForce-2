import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ManagerLeaveService } from '../../../core/services/manager-leave.service';

@Component({
  selector: 'app-approve-leaves',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './approve-leaves.component.html'
})
export class ApproveLeavesComponent implements OnInit {

  managerId: number | null = null;
  leaves: any[] = [];

  constructor(private managerLeaveService: ManagerLeaveService) {}

  ngOnInit(): void {}

  loadLeaves() {

    if (!this.managerId) {
      alert("Please enter Manager ID");
      return;
    }

    this.managerLeaveService.getTeamLeaves(this.managerId).subscribe({
      next: (data) => {
        this.leaves = data;
      },
      error: (err) => {
        console.error("Failed to load leaves", err);
        alert("Failed to load leaves");
      }
    });

  }

 approveLeave(id: number) {

  this.managerLeaveService.approveLeave(id).subscribe({
    next: () => {
      alert("Leave approved");

      // reload the table
      this.loadLeaves();

    },
    error: () => {
      alert("Failed to approve");
    }
  });

}

rejectLeave(id: number) {

  this.managerLeaveService.rejectLeave(id).subscribe({
    next: () => {
      alert("Leave Rejected");
      this.loadLeaves();
    },
    error: () => {
      alert("Failed to reject leave");
    }
  });

}

}