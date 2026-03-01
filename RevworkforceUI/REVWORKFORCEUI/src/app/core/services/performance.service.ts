import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PerformanceService {

  private baseUrl = 'http://localhost:8080/api/performance';

  constructor(private http: HttpClient) {}

  getTeamReviews(managerId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/team?managerId=${managerId}`);
  }

  createReview(employeeId: number, managerId: number, data: any) {
    return this.http.post(
      `${this.baseUrl}/review?employeeId=${employeeId}&managerId=${managerId}`,
      data
    );
  }

  updateReview(reviewId: number, data: any) {
    return this.http.put(`${this.baseUrl}/${reviewId}`, data);
  }

  getMyReviews(employeeId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/my-reviews?employeeId=${employeeId}`);
  }
}
