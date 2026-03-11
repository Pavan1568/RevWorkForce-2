import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PerformanceService } from '../../../core/services/performance.service';

@Component({
  selector: 'app-employee-performance',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './performance.component.html'
})
export class PerformanceComponent {

  employeeId: number | null = null;
  reviews: any[] = [];

  constructor(private performanceService: PerformanceService) {}

  loadReviews() {
    if (!this.employeeId) {
      alert('Please enter your Employee ID');
      return;
    }
    this.performanceService.getMyReviews(this.employeeId).subscribe({
      next: (data) => {
        this.reviews = data;
      },
      error: (err) => {
        console.error('Failed to load reviews', err);
        alert('Failed to load reviews');
      }
    });
  }
}
