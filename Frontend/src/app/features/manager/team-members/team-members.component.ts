import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeService } from '../../../core/services/employee.service';

@Component({
  selector: 'app-team-members',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './team-members.component.html'
})
export class TeamMembersComponent implements OnInit {

  teamMembers: any[] = [];

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    const managerId = Number(localStorage.getItem('employeeId'));

    if (managerId) {
      this.employeeService.getTeamMembers(managerId).subscribe({
        next: (data) => this.teamMembers = data,
        error: () => alert('Failed to load team members')
      });
    }
  }
}