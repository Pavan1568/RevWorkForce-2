import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmployeeProfileService {

  private baseUrl = 'http://localhost:8080/api/profile';

  constructor(private http: HttpClient) {}

  getProfile(employeeId: number) {
    return this.http.get<any>(`${this.baseUrl}/${employeeId}`);
  }
}

