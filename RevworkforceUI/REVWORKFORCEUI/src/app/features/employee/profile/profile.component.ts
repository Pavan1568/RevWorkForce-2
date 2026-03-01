import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeProfileService } from '../../../core/services/employee-profile.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html'
})
export class ProfileComponent {

  employeeId: number | null = null;
  profile: any;
  isLoading = false;

  constructor(private profileService: EmployeeProfileService) {}

  loadProfile() {
    if (!this.employeeId) {
      alert('Please enter your Employee ID');
      return;
    }

    this.isLoading = true;
    this.profileService.getProfile(this.employeeId).subscribe({
      next: (data) => {
        this.profile = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to load profile', err);
        alert('Failed to load profile');
        this.isLoading = false;
      }
    });
  }
}
