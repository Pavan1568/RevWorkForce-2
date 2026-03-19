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

  approveLeave(id: number) {
  return this.http.put(
    `http://localhost:8080/api/leaves/${id}/approve?comment=Approved`,
    {}
  );
}

rejectLeave(id: number) {
  return this.http.put(
    `http://localhost:8080/api/leaves/${id}/reject?comment=Rejected`,
    {}
  );
}

// rejectLeave(id: number) {
//   return this.http.put(`${this.baseUrl}/${id}/reject`, {});
// }


getTeamLeaveCalendar(managerId: number) {
  return this.http.get<any[]>(
    `${this.baseUrl}/manager/${managerId}/calendar`
  );
}

  getEmployeeLeaveBalance(employeeId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/manager/employee/${employeeId}/balance`);
  }
}
