import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LeaveTypeService {

  private baseUrl = 'http://localhost:8080/api/leave-types';

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<any[]>(this.baseUrl);
  }

  createLeaveType(data: any) {
  return this.http.post<any>(this.baseUrl, data);
}

  delete(id: number) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}

