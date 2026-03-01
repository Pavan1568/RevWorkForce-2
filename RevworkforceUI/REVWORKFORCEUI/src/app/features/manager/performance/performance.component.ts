import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PerformanceService } from '../../../core/services/performance.service';

@Component({
  selector: 'app-performance',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './performance.component.html'
})
export class PerformanceComponent {

  managerId: number | null = null;
  reviews: any[] = [];
  newReview: any = {
    employeeId: null,
    reviewPeriod: '',
    rating: 5,
    feedback: '',
    reviewDate: new Date().toISOString().slice(0, 10)
  };
  isSubmitting = false;

  constructor(private performanceService: PerformanceService) {}

  loadReviews() {
    if (!this.managerId) {
      alert('Please enter Manager ID');
      return;
    }
    this.performanceService.getTeamReviews(this.managerId).subscribe({
      next: (data) => {
        this.reviews = data;
      },
      error: (err) => {
        console.error('Failed to load reviews', err);
        alert('Failed to load reviews');
      }
    });
  }

  submitReview() {
    if (!this.managerId || !this.newReview.employeeId || !this.newReview.reviewPeriod || !this.newReview.reviewDate) {
      alert('Manager ID, Employee ID, Review Period and Review Date are required');
      return;
    }
    this.isSubmitting = true;
    const body = {
      reviewPeriod: this.newReview.reviewPeriod,
      rating: this.newReview.rating,
      feedback: this.newReview.feedback || '',
      reviewDate: this.newReview.reviewDate
    };
    this.performanceService.createReview(
      this.newReview.employeeId,
      this.managerId,
      body
    ).subscribe({
      next: () => {
        alert('Review submitted');
        this.newReview = { employeeId: null, reviewPeriod: '', rating: 5, feedback: '', reviewDate: new Date().toISOString().slice(0, 10) };
        this.loadReviews();
        this.isSubmitting = false;
      },
      error: (err) => {
        console.error(err);
        alert('Failed to submit review');
        this.isSubmitting = false;
      }
    });
  }
}
