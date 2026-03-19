import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LeaveTypeService } from '../../../core/services/leave-type.service';

@Component({
  selector: 'app-leavetypes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './leavetypes.component.html'
})
export class LeavetypesComponent implements OnInit {

  leaveTypes: any[] = [];

  newLeaveType: any = {
    name: '',
    description: '',
    totalDays: null
  };

  isSubmitting = false;

  constructor(private leaveTypeService: LeaveTypeService) {}

  ngOnInit(): void {
    this.loadLeaveTypes();
  }

  loadLeaveTypes() {
    this.leaveTypeService.getAll().subscribe({
      next: (data) => {
        this.leaveTypes = data;
      },
      error: (err) => {
        console.error('Failed to load leave types', err);
        alert('Failed to load leave types');
      }
    });
  }

  createLeaveType() {
    if (!this.newLeaveType.name) {
      alert('Leave type name is required');
      return;
    }

    this.isSubmitting = true;

    this.leaveTypeService.createLeaveType(this.newLeaveType).subscribe({
      next: () => {
        alert('Leave type created successfully');
        this.newLeaveType = { name: '', description: '' };
        this.loadLeaveTypes();
        this.isSubmitting = false;
      },
      error: (err:any) => {
        console.error('Failed to create leave type', err);
        alert('Failed to create leave type');
        this.isSubmitting = false;
      }
    });
  }

  deleteLeaveType(lt: any) {
    if (!confirm(`Delete leave type "${lt.name}"?`)) {
      return;
    }

    this.leaveTypeService.delete(lt.id).subscribe({
      next: () => {
        alert('Leave type deleted successfully');
        this.loadLeaveTypes();
      },
      error: (err) => {
        console.error('Failed to delete leave type', err);
        alert('Failed to delete leave type');
      }
    });
  }
}
