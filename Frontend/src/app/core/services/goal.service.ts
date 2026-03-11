import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GoalService {

  private baseUrl = 'http://localhost:8080/api/goals';

  constructor(private http: HttpClient) {}

  getGoals(employeeId: number) {
    return this.http.get<any[]>(this.baseUrl + '?employeeId=' + employeeId);
  }

  create(employeeId: number, data: any) {
    return this.http.post(this.baseUrl + '?employeeId=' + employeeId, data);
  }

  update(goalId: number, data: any) {
    return this.http.put(`${this.baseUrl}/${goalId}`, data);
  }

  updateProgress(goalId: number, progress: number) {
    return this.http.patch(`${this.baseUrl}/${goalId}/progress?progress=${progress}`, {});
  }

  delete(goalId: number) {
    return this.http.delete(`${this.baseUrl}/${goalId}`);
  }
}
