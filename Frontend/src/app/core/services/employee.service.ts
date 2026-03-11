import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  getManagers() {
  return this.http.get<any[]>(
    `${this.baseUrl}/managers`);
}

assignManager(employeeId: number, managerId: number) {
  return this.http.put(
    `http://localhost:8080/api/employees/${employeeId}/assign-manager/${managerId}`,
    {}
  );
}

  private baseUrl = 'http://localhost:8080/api/employees';

  constructor(private http: HttpClient) {}

  // Admin: get all employees by using name search with empty value
 getAll() {
  return this.http.get<any[]>(this.baseUrl);
}

getTeamMembers(managerId: number) {
  return this.http.get<any[]>(
    `http://localhost:8080/api/employees/manager/${managerId}/team`
  );
}

  // Admin: create employee for an existing user
  createForUser(userId: number, data: any) {
  return this.http.post(
    `${this.baseUrl}/assign-user/${userId}`,
    data,
    { responseType: 'text' }   // 👈 ADD THIS
  );
}

}



