import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ManagerLeaveService {

  private baseUrl = 'http://localhost:8080/api/leaves';

  constructor(private http: HttpClient) {}

  getTeamLeaves(managerId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/manager/${managerId}`);
  }

  approveLeave(leaveId: number, comment: string) {
    return this.http.put(`${this.baseUrl}/${leaveId}/approve?comment=${encodeURIComponent(comment)}`, {});
  }

  rejectLeave(leaveId: number, comment: string) {
    return this.http.put(`${this.baseUrl}/${leaveId}/reject?comment=${encodeURIComponent(comment)}`, {});
  }

  getEmployeeLeaveBalance(employeeId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/manager/employee/${employeeId}/balance`);
  }
}
