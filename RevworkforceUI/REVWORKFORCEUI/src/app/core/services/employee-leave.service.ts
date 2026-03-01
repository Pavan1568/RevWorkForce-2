import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmployeeLeaveService {

  private baseUrl = 'http://localhost:8080/api/leaves';

  constructor(private http: HttpClient) {}

  getMyLeaves(employeeId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/employee/${employeeId}`);
  }

  applyLeave(data: any) {
    return this.http.post(`${this.baseUrl}/apply`, data);
  }

  getTeamLeaves(managerId: number) {
  return this.http.get<any[]>(
    `http://localhost:8080/api/leaves/manager/${managerId}`
  );
}

getLeaveBalance(employeeId: number) {
  return this.http.get<any[]>(
    `http://localhost:8080/api/leaves/manager/employee/${employeeId}/balance`
  );
}
}

