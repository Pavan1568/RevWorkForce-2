import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeLeaveService } from '../../../core/services/employee-leave.service';
import { LeaveTypeService } from '../../../core/services/leave-type.service';

@Component({
  selector: 'app-my-leaves',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-leaves.component.html'
})
export class MyLeavesComponent implements OnInit {

  employeeId: number | null = null;

  myLeaves: any[] = [];

  leaveTypes: any[] = [];

  constructor(
    private employeeLeaveService: EmployeeLeaveService,
    private leaveTypeService: LeaveTypeService
  ) {}

  ngOnInit(): void {

    const id = localStorage.getItem('employeeId');

    if (id) {
      this.employeeId = Number(id);
    }

    if (this.employeeId) {
      this.employeeLeaveService.getMyLeaves(this.employeeId)
        .subscribe((data:any) => {
          this.myLeaves = data;
        });
    }

    this.leaveTypeService.getAll()
      .subscribe((data:any) => {
        this.leaveTypes = data;
      });

  }

  getLeaveTypeName(id: number) {

    const type = this.leaveTypes.find(t => t.id === id);

    return type ? type.name : id;

  }

}