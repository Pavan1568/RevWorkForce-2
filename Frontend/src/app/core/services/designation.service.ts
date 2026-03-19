import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DesignationService {

  private baseUrl = 'http://localhost:8080/api/admin/designations';

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<any[]>(this.baseUrl);
  }
}